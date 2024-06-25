package fr.agrouagrou.mobileapp.ui.gamephases

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.agrouagrou.grpc_server.R
import fr.agrouagrou.mobileapp.ui.common.GameViewModel

@Composable
fun GamePhaseGameMasterRoute() {
    Column(Modifier.padding(top = 48.dp, start = 16.dp)) {
        Text(
            text = stringResource(R.string.you_are_gamemaster),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}