package fr.agrouagrou.mobileapp.ui.createjoingame

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.agrouagrou.grpc_server.R
import fr.agrouagrou.mobileapp.IpPortEncDec
import fr.agrouagrou.mobileapp.getPrivateIpAddress
import fr.agrouagrou.mobileapp.ui.common.GameViewModel
import fr.agrouagrou.mobileapp.ui.components.GameIDInfo
import fr.agrouagrou.mobileapp.ui.components.PlayerList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun CreateGameRoute(
    gameViewModel: GameViewModel,
    onStartGame: (() -> Unit)? = null,
) {
    var ipAddress: String? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        ipAddress = withContext(Dispatchers.IO) { getPrivateIpAddress() }
    }
    val gameId = if (ipAddress != null) IpPortEncDec.encodeIpPort(ipAddress!!, 50051) else null

    val gameManager by gameViewModel.gameManager.collectAsState()
    var players by remember { mutableStateOf(gameManager.playerManager.players.values.toList()) }
    var canStartGame by remember { mutableStateOf(gameManager.canStartGame()) }
    LaunchedEffect(Unit) {
        gameViewModel.watchForPlayerUpdates {
            players = it
            canStartGame = gameManager.canStartGame()
        }
    }

    Column(
        Modifier
            .padding(top = 48.dp, bottom = 42.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Header(gameId, LocalContext.current)
        PlayerList(players)
        if (canStartGame) StartGameButton(onStartGame)
    }
}

@Composable
fun Header(gameId: String?, context: Context) {
    Column(Modifier.padding(start = 16.dp, bottom = 16.dp)) {
        Text(
            text = stringResource(R.string.waiting_for_players),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        gameId?.let {
            GameIDInfo(it, context)
        }
    }
}

@Composable
fun StartGameButton(onStartGame: (() -> Unit)?) {
    Column(Modifier.padding(start = 16.dp, top = 16.dp)) {
        Button(onClick = { onStartGame?.let { it() } }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = null,
                )
                Text(text = stringResource(id = R.string.start_game))
            }
        }
    }
}
