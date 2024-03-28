package fr.agrouagrou.mobileapp.ui.screens

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.agrouagrou.grpc_server.R

@Preview
@Composable
fun HomeScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(16.dp)
    ) {
        Text(text = stringResource(id = R.string.home_title), fontSize = 30.sp)
        Button(onClick = { /*TODO*/ }) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                Text(text = stringResource(id = R.string.home_create_game))
            }
        }
        Button(onClick = { /*TODO*/ }) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                Text(text = stringResource(id = R.string.home_join_game))
            }
        }
    }
}