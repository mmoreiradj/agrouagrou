package fr.agrouagrou.mobileapp.ui.createjoingame

import android.util.Log
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.agrouagrou.grpc_server.R
import fr.agrouagrou.mobileapp.ui.components.JoinGameForm

@Composable
fun JoinGameRoute() {
    val username = remember { mutableStateOf("") }
    val gameCode = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(space = 16.dp, alignment = Alignment.CenterVertically),
        modifier = Modifier.fillMaxSize(),
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
                Log.d(
                    "JoinGameRoute",
                    "Joining game with username $username and game code $gameCode"
                )
            }
        )

    }
}

@Preview
@Composable
fun JoinGameRoutePreview() {
    JoinGameRoute()
}
