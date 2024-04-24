package fr.agrouagrou.mobileapp.ui.screens

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.agrouagrou.grpc_server.R

@Preview
@Composable
fun JoinGameScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(space = 16.dp, alignment = Alignment.CenterVertically),
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = stringResource(id = R.string.joingame_title), fontSize = 30.sp)
        JoinGameForm()
    }
}

@Preview
@Composable
fun JoinGameForm() {
    var username by remember { mutableStateOf("") }
    var gameCode by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(16.dp, alignment = Alignment.CenterVertically),
        modifier = Modifier,
    ) {
        TextField(value = username, onValueChange = { username = it },
            label = { Text(text = stringResource(id = R.string.joingame_form_username)) }
        )
        TextField(value = gameCode, onValueChange = { gameCode = it },
            label = { Text(text = stringResource(id = R.string.joingame_form_game_code)) }
        )
        Button(onClick = { /*TODO*/ }) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                Text(text = stringResource(id = R.string.joingame_form_join_game))
            }
        }
    }
}