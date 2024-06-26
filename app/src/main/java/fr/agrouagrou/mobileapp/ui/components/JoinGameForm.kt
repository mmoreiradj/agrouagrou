package fr.agrouagrou.mobileapp.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.agrouagrou.grpc_server.R

@Composable
fun JoinGameForm(
    username: String,
    gameCode: String,
    onUsernameChange: (String) -> Unit,
    onGameCodeChange: (String) -> Unit,
    onJoinGame: (String, String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text(text = stringResource(id = R.string.joingame_form_username)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        OutlinedTextField(
            value = gameCode,
            onValueChange = onGameCodeChange,
            label = { Text(text = stringResource(id = R.string.joingame_form_game_code)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Button(onClick = { onJoinGame(username, gameCode) }) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                Text(text = stringResource(id = R.string.joingame_form_join_game))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JoinGameFormPreview() {
    var username by remember { mutableStateOf("") }
    var gameCode by remember { mutableStateOf("") }
    JoinGameForm(
        username = username,
        gameCode = gameCode,
        onUsernameChange = { username = it },
        onGameCodeChange = { gameCode = it }
    ) { _, _ ->
        Log.d("JoinGameFormPreview", "Username: $username, Game code: $gameCode")
    }
}
