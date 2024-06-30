package fr.agrouagrou.mobileapp.ui.gameroutes

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.PlayerStatus
import fr.agrouagrou.grpc_server.R
import fr.agrouagrou.mobileapp.toCard
import fr.agrouagrou.mobileapp.ui.common.ClientPlayerViewModel
import fr.agrouagrou.mobileapp.ui.gamephases.CardView
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

@Composable
fun GameRunningRoute(
    gameState: GameState,
    clientPlayerViewModel: ClientPlayerViewModel,
) {
    val playerState by clientPlayerViewModel.flowPlayerState.collectAsState()

    LaunchedEffect(Unit) {
        clientPlayerViewModel.connectToServer()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        when (playerState.status) {
            PlayerStatus.DEAD -> {
                DeathScreen(onClick = { /*TODO*/ })
            }

            else -> {
                when (gameState) {
                    GameState.LOOKING_FOR_PLAYERS -> LookingForPlayers()
                    GameState.NIGHT_START -> NightScreen(player = playerState)
                    GameState.NIGHT_FORTUNE_TELLER -> NightScreen(player = playerState)
                    GameState.NIGHT_WEREWOLF -> NightScreen(player = playerState)
                    GameState.NIGHT_WITCH -> NightScreen(player = playerState)
                    GameState.DAY_DEBATE -> DayScreen(
                        player = playerState, stringResource(id = R.string.day_debate)
                    )

                    GameState.DAY_VOTE -> DayScreen(
                        player = playerState, stringResource(id = R.string.day_vote)
                    )
                }
            }
        }
    }
}

@Composable
fun LookingForPlayers() {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.waiting_for_players))
        CardView()
    }
}

private fun vibratePhone(context: Context) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    val pattern = longArrayOf(0, 200)
    vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
}

@Composable
fun NightScreen(player: Player) {
    val context = LocalContext.current
    val sensorManager = remember { ContextCompat.getSystemService(context, SensorManager::class.java)!! }
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val z = it.values[2]

                    if (z >= 7.0) {
                        Log.d("NightScreen", "Screen is facing up")
                        vibratePhone(context)
                    } else {
                        Log.d("NightScreen", "Screen is facing down")
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        if (player.status == PlayerStatus.ALIVE) {
            sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.night_screen))
        CardView(player.role.toCard())
    }
}

@Composable
fun DeathScreen(onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.you_are_dead))
        CardView()
        Button(onClick) {
            Text(text = stringResource(id = R.string.exit_game))
        }
    }
}

@Composable
fun DayScreen(player: Player, message: String) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        CardView(player.role.toCard())
    }
}
