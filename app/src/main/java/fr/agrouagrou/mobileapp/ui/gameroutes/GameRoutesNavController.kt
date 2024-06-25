package fr.agrouagrou.mobileapp.ui.gameroutes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.grpc_server.R
import fr.agrouagrou.mobileapp.toCard
import fr.agrouagrou.mobileapp.ui.common.ClientGameViewModel
import fr.agrouagrou.mobileapp.ui.common.ClientPlayerViewModel
import fr.agrouagrou.mobileapp.ui.gamephases.CardView
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

@Composable
fun GameRunningRoute(
    channel: ManagedChannel,
    clientGameViewModel: ClientGameViewModel = ClientGameViewModel(channel),
    clientPlayerViewModel: ClientPlayerViewModel = ClientPlayerViewModel(channel, "username")
) {
    val gameState by clientGameViewModel.flowGameState.collectAsState()
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
        when (gameState) {
            GameState.LOOKING_FOR_PLAYERS -> LookingForPlayers()
            GameState.NIGHT_START -> NightScreen(player = playerState)
            GameState.NIGHT_FORTUNE_TELLER -> NightScreen(player = playerState)
            GameState.NIGHT_WEREWOLF -> NightScreen(player = playerState)
            GameState.NIGHT_WITCH -> NightScreen(player = playerState)
        }
    }
}

@Composable
fun LookingForPlayers() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.waiting_for_players))
        CardView()
    }
}

@Composable
fun NightScreen(player: Player) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.night_screen))
        CardView(player.role.toCard())
    }
}

@Preview
@Composable
fun GamesRoutesNavControllerPreview() {
    GameRunningRoute(
        ManagedChannelBuilder.forAddress("10.0.2.2", 50051).usePlaintext().build()
    )
}
