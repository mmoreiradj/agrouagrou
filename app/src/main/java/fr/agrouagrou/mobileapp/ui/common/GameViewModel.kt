package fr.agrouagrou.mobileapp.ui.common

import android.util.Log
import androidx.lifecycle.ViewModel
import fr.agrouagrou.common.GameManager
import fr.agrouagrou.common.GameRules
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.PlayerRegistry.Notification
import fr.agrouagrou.common.service.DebugService
import fr.agrouagrou.common.service.GameStateService
import fr.agrouagrou.common.service.PlayerService
import fr.agrouagrou.common.service.roles.FortuneTellerService
import fr.agrouagrou.common.service.roles.WerewolfService
import fr.agrouagrou.common.service.roles.WitchService
import io.grpc.Grpc
import io.grpc.InsecureServerCredentials
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _gameManager = MutableStateFlow(GameManager(GameRules(3, 1)));
    val gameManager: StateFlow<GameManager> = _gameManager.asStateFlow()

    private val server = Grpc.newServerBuilderForPort(50051, InsecureServerCredentials.create())
        .addService(GameStateService(_gameManager.value))
        .addService(PlayerService(_gameManager.value.playerManager))
        .addService(DebugService(_gameManager.value))
        .addService(FortuneTellerService(_gameManager.value))
        .addService(WerewolfService(_gameManager.value))
        .addService(WitchService(_gameManager.value))
        .build()

    fun createGame() {
        server.start()
        Log.d("GameViewModel", "Server started, listening on 50051")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                Log.d("GameViewModel", "Shutting down the server")
                server.shutdown()
            }
        )
    }

    suspend fun watchForPlayerUpdates(cb: (List<Player>) -> Unit) {
        gameManager.value.playerManager.notifications.collect { notification ->
            when (notification) {
                is Notification.PlayerRegistered -> {
                    cb(gameManager.value.playerManager.players.values.toList())
                }

                is Notification.PlayerUnregistered -> {
                    cb(gameManager.value.playerManager.players.values.toList())
                }
            }
        }
    }
}
