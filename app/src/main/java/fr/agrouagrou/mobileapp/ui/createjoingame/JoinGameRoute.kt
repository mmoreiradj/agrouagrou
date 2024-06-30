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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.agrouagrou.grpc_server.BuildConfig
import fr.agrouagrou.grpc_server.R
import fr.agrouagrou.mobileapp.IpPortEncDec
import fr.agrouagrou.mobileapp.ui.components.JoinGameForm
import fr.agrouagrou.proto.PlayerGrpcKt
import fr.agrouagrou.proto.PlayerRegisterRequest
import io.grpc.okhttp.OkHttpChannelBuilder
import kotlinx.coroutines.launch

suspend fun testConnectivity(ip: String, port: Int, username: String) {
    val channel = OkHttpChannelBuilder.forAddress(ip, port).usePlaintext().build()
    val stub = PlayerGrpcKt.PlayerCoroutineStub(channel)
    val request = stub.register(
        PlayerRegisterRequest
            .newBuilder()
            .setUsername(username)
            .build()
    )
    Log.d("JoinGameRoute", "Response: $request")
}

@Composable
fun JoinGameRoute() {
    val username = remember { mutableStateOf("") }
    val gameCode = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

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
                val ipPort = if (BuildConfig.DEBUG) {
                    Pair("10.0.2.2", 50051)
                } else {
                    IpPortEncDec.decodeIpPort(gameCode)
                }
                Log.d("JoinGameRoute", "Decoded IP and port: $ipPort")
                if (ipPort != null) {
                    scope.launch {
                        testConnectivity("10.0.2.2", ipPort.second, username)
                    }
                }
            }
        )
    }
}

/*
@Preview
@Composable
fun JoinGameRoutePreview() {
    JoinGameRoute(gameUiState)
}
*/