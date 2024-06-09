package fr.agrouagrou.mobileapp.ui.createjoingame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.State
import fr.agrouagrou.grpc_server.R
import fr.agrouagrou.mobileapp.ui.components.GameIDInfo
import fr.agrouagrou.mobileapp.ui.components.PlayerList
import java.util.UUID

@Composable
fun CreateGameRoute() {
    val gameID = "ABCD"
    val context = LocalContext.current

    Column {
        Text(
            text = stringResource(R.string.waiting_for_players),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        GameIDInfo(gameID, context, modifier = Modifier.padding(bottom = 16.dp))
        PlayerList(players = listOf(
            Player("Player 1", UUID.randomUUID(), State.ALIVE),
            Player("Player 2", UUID.randomUUID(), State.ALIVE),
            Player("Player 3", UUID.randomUUID(), State.ALIVE),
        ))
    }
}

@Preview
@Composable
fun CreateGameRoutePreview() {
    CreateGameRoute()
}
