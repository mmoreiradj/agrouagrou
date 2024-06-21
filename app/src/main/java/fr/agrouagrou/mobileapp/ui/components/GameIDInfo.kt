package fr.agrouagrou.mobileapp.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import fr.agrouagrou.grpc_server.R

@Composable
fun GameIDInfo(gameID: String, context: Context, modifier: Modifier = Modifier) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, gameID)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.waiting_for_other_players),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.your_game_id_is, gameID),
            color = MaterialTheme.colorScheme.primary
        )
        Button(onClick = {
            startActivity(context, shareIntent, null)
        }) {
            Text(text = stringResource(R.string.copy_game_id))
        }
    }
}

@Preview
@Composable
fun GameIDInfoPreview() {
    GameIDInfo("ABCD", LocalContext.current)
}
