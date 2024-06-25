package fr.agrouagrou.mobileapp.ui.createjoingame

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import fr.agrouagrou.common.GameState
import fr.agrouagrou.mobileapp.ui.common.ClientGameViewModel
import fr.agrouagrou.mobileapp.ui.common.ClientPlayerViewModel
import io.grpc.ManagedChannel

@Composable
fun WaitingRoomRoute(channel: ManagedChannel) {
    val clientGameViewModel = ClientGameViewModel(channel)
    val gameState by clientGameViewModel.flowGameState.collectAsState()

    val clientPlayerViewModel = ClientPlayerViewModel(channel, "username")
    val playerState by clientPlayerViewModel.flowPlayerState.collectAsState()

    LaunchedEffect(Unit) {
        Log.d("WaitingRoomRoute", "Connecting to server")
        clientPlayerViewModel.connectToServer()
    }

    val text = when (gameState) {
        GameState.LOOKING_FOR_PLAYERS -> "Looking for players"
        GameState.NIGHT_START -> "Night start"
        GameState.NIGHT_FORTUNE_TELLER -> "Night fortune teller"
        GameState.NIGHT_WEREWOLF -> "Night werewolf"
        GameState.NIGHT_WITCH -> "Night witch"
    }

    Column {
        Text(text)
        Text(text = playerState.id.toString())
    }
}
