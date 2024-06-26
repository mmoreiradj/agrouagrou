package fr.agrouagrou.mobileapp.ui.gamephases

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.PlayerStatus
import fr.agrouagrou.common.player.Role
import fr.agrouagrou.grpc_server.R
import fr.agrouagrou.mobileapp.ui.common.GameViewModel
import fr.agrouagrou.mobileapp.ui.common.extensions.toLabel
import fr.agrouagrou.mobileapp.ui.components.PlayerList

@Composable
fun GamePhaseGameMasterRoute(gameViewModel: GameViewModel) {
    val gameManager by gameViewModel.gameManager.collectAsState()
    val gameState by gameManager.gameState.collectAsState()

    var players by remember { mutableStateOf(gameManager.playerManager.players.values.toList()) }
    var canStartGame by remember { mutableStateOf(gameManager.canStartGame()) }
    LaunchedEffect(Unit) {
        gameViewModel.watchForPlayerUpdates {
            players = it
            canStartGame = gameManager.canStartGame()
        }
    }

    fun villageVote(playerToKill: Player) {
        for (player in players.filter { p -> p.status !== PlayerStatus.DEAD }) {
            gameManager.currentTurn().villagerActions.voteVictim(
                player.id.toString(),
                playerToKill.id.toString()
            )
        }
    }

    fun werewolfVote(playerToKill: Player) {
        for (player in players.filter { p -> p.role == Role.Werewolf }) {
            gameManager.currentTurn().werewolfActions.voteVictim(
                player.id.toString(),
                playerToKill.id.toString()
            )
        }
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    // will crash when werewolf/player's turn to vote
                    gameManager.nextGameState()
                },
                icon = { Icon(Icons.Rounded.PlayArrow, "Next turn button") },
                text = { Text(stringResource(R.string.next_turn)) }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(Modifier.padding(start = 16.dp)) {
                Text(
                    text = stringResource(R.string.you_are_gamemaster),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = "${stringResource(R.string.game_status)} ${stringResource(gameState.toLabel())}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                if (gameState === GameState.DAY_VOTE) {
                    Text("Which player does the village choose to kill?")
                }
                if (gameState === GameState.NIGHT_WEREWOLF) {
                    Text("Who has to die this night? \uD83D\uDC3A")
                }
                Text(
                    text = stringResource(R.string.players_list),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            PlayerList(players.filter { p -> p.status !== PlayerStatus.DEAD }, onClick = { playerToKill ->
                when (gameState) {
                    GameState.DAY_VOTE -> villageVote(playerToKill)
                    GameState.NIGHT_WEREWOLF -> werewolfVote(playerToKill)
                    else -> {}
                }
            })
        }
    }
}