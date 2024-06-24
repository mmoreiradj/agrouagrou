package fr.agrouagrou.mobileapp.ui.createjoingame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
) {
    val context = LocalContext.current
    var ipAddress: String? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        ipAddress = withContext(Dispatchers.IO) { getPrivateIpAddress() }
    }
    val port = 50051
    val gameId = if (ipAddress != null) IpPortEncDec.encodeIpPort(ipAddress!!, port) else null

    val gameManager by gameViewModel.gameManager.collectAsState()
    var players by remember { mutableStateOf(gameManager.playerManager.players.values.toList()) }
    LaunchedEffect(Unit) {
        gameViewModel.watchForPlayerUpdates { players = it }
    }

    Column {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 42.dp, start = 16.dp, end = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.waiting_for_players),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            if (gameId != null) GameIDInfo(
                gameId,
                context,
                modifier = Modifier.padding(bottom = 16.dp)
            ) else null
        }
        PlayerList(
            players = players,
        )
    }
}

/**
@Preview
@Composable
fun CreateGameRoutePreview() {
    CreateGameRoute(gameUiState)
}
*/
