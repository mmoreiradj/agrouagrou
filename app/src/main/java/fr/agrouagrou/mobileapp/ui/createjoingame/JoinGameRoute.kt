package fr.agrouagrou.mobileapp.ui.createjoingame

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import fr.agrouagrou.grpc_server.R
import fr.agrouagrou.mobileapp.ui.components.JoinGameForm

@Composable
fun JoinGameRoute() {
    val username = remember { mutableStateOf("") }
    val gameCode = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.join_game),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge
        )
        JoinGameForm(
            username = username.value,
            gameCode = gameCode.value,
            onUsernameChange = { username.value = it },
            onGameCodeChange = { gameCode.value = it },
            onJoinGame = { username, gameCode ->
                Log.d("JoinGameRoute", "Joining game with username $username and game code $gameCode")
            }
        )

    }
}

@Preview
@Composable
fun JoinGameRoutePreview() {
    JoinGameRoute()
}
