package fr.agrouagrou.mobileapp.ui.gameroutes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.PlayerStatus
import fr.agrouagrou.grpc_server.R
import fr.agrouagrou.mobileapp.toCard
import fr.agrouagrou.mobileapp.ui.common.ClientPlayerViewModel
import fr.agrouagrou.mobileapp.ui.gamephases.CardView
import io.grpc.ManagedChannel

@Composable
fun GameRunningRoute(
    channel: ManagedChannel,
    gameState: GameState,
    clientPlayerViewModel: ClientPlayerViewModel = ClientPlayerViewModel(channel, "username"),
) {
    val playerState by clientPlayerViewModel.flowPlayerState.collectAsState()

    LaunchedEffect(Unit) {
        clientPlayerViewModel.connectToServer()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        when (playerState.status) {
            PlayerStatus.DEAD -> {
                DeathScreen(onClick = { /*TODO*/ })
            }

            else -> {
                when (gameState) {
                    GameState.LOOKING_FOR_PLAYERS -> LookingForPlayers()
                    GameState.NIGHT_START -> NightScreen(player = playerState)
                    GameState.NIGHT_FORTUNE_TELLER -> NightScreen(player = playerState)
                    GameState.NIGHT_WEREWOLF -> NightScreen(player = playerState)
                    GameState.NIGHT_WITCH -> NightScreen(player = playerState)
                    GameState.DAY_DEBATE -> DayScreen(
                        player = playerState, stringResource(id = R.string.day_debate)
                    )

                    GameState.DAY_VOTE -> DayScreen(
                        player = playerState, stringResource(id = R.string.day_vote)
                    )
                }
            }
        }
    }
}

@Composable
fun LookingForPlayers() {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.waiting_for_players))
        CardView()
    }
}

@Composable
fun NightScreen(player: Player) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.night_screen))
        CardView(player.role.toCard())
    }
}

@Composable
fun DeathScreen(onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.you_are_dead))
        CardView()
        Button(onClick) {
            Text(text = stringResource(id = R.string.exit_game))
        }
    }
}

@Composable
fun DayScreen(player: Player, message: String) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        CardView(player.role.toCard())
    }
}
