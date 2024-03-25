package fr.agrouagrou.mobileapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.grpc.okhttp.OkHttpChannelBuilder
import kotlinx.coroutines.launch
import fr.agrouagrou.mobileapp.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val server = HelloWorldServer(9090)

        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Greeting(server)
                }
            }
        }
    }
}

@Composable
fun Greeting(server: HelloWorldServer, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val context = LocalContext.current
        val composableScope = rememberCoroutineScope()
        var serverButtonEnabled by remember { mutableStateOf(true) }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                server.start()

                serverButtonEnabled = false
                Toast.makeText(context, "Server started", Toast.LENGTH_LONG).show()
            }, enabled = serverButtonEnabled) {
                Text("Start server")
            }
            Button(onClick = {
                val channel = OkHttpChannelBuilder
                    .forAddress("10.0.2.2", 8080)
                    .usePlaintext()
                    .build()

                val client = HelloWorldClient(channel)

                composableScope.launch {
                    val result = client.add(60, 9)
                    Toast.makeText(context, "Result is: $result", Toast.LENGTH_LONG).show()
                }
            }) {
                Text("Add two numbers")
            }
        }
    }
}