package fr.agrouagrou.mobileapp.ui.gamephases

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import fr.agrouagrou.grpc_server.R
import fr.agrouagrou.mobileapp.ui.common.GameViewModel
import fr.agrouagrou.mobileapp.ui.components.PlayerList

@Composable
fun GamePhaseGameMasterRoute(gameViewModel: GameViewModel) {
    val gameManager by gameViewModel.gameManager.collectAsState()
    var players by remember { mutableStateOf(gameManager.playerManager.players.values.toList()) }
    var canStartGame by remember { mutableStateOf(gameManager.canStartGame()) }
    LaunchedEffect(Unit) {
        gameViewModel.watchForPlayerUpdates {
            players = it
            canStartGame = gameManager.canStartGame()
        }
    }

    Column(Modifier.padding(top = 48.dp)) {
        Text(
            text = stringResource(R.string.you_are_gamemaster),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        Text(
            text = "${stringResource(R.string.game_status)} ${gameManager.gameState.value}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )
        Column(
            Modifier
                .padding(start = 16.dp, bottom = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.players_list),
                style = MaterialTheme.typography.titleLarge
            )
            PlayerList(players)
        }
    }
}