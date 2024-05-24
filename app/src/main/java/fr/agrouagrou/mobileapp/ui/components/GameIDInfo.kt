package fr.agrouagrou.mobileapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import fr.agrouagrou.grpc_server.R

@Composable
fun GameIDInfo(gameID: String) {
    Column {
        Text(
            text = stringResource(R.string.waiting_for_other_players),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.your_game_id_is, gameID),
            color = MaterialTheme.colorScheme.primary
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(R.string.copy_game_id))
        }
    }
}

@Preview
@Composable
fun GameIDInfoPreview() {
    GameIDInfo("ABCD")
}
