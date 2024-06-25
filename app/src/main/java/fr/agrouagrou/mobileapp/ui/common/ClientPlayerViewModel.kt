package fr.agrouagrou.mobileapp.ui.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.protobuf.Empty
import fr.agrouagrou.common.extensions.toPlayerStatus
import fr.agrouagrou.common.extensions.toRole
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.proto.PlayerGrpcKt
import fr.agrouagrou.proto.PlayerRegisterRequest
import io.grpc.ManagedChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

const val PLAYER_POLLING_DELAY = 5000L

class ClientPlayerViewModel(channel: ManagedChannel, username: String) : ViewModel() {
    private val _flowPlayerState: MutableStateFlow<Player> = MutableStateFlow(Player(username))
    val flowPlayerState: StateFlow<Player> = _flowPlayerState

    private val stub = PlayerGrpcKt.PlayerCoroutineStub(channel)

    fun connectToServer() {
        viewModelScope.launch {
            try {
                val response = stub.register(
                    PlayerRegisterRequest.newBuilder().setUsername(flowPlayerState.value.username)
                        .build()
                )
                val updatedPlayer = _flowPlayerState.value.copy(id = UUID.fromString(response.id))
                _flowPlayerState.update { updatedPlayer }
                streamPlayerState()
                Log.d(
                    "ClientPlayerViewModel",
                    "Registered player with id: ${flowPlayerState.value.id}"
                )
            } catch (e: Exception) {
                // TODO: handle exception
                e.printStackTrace()
                Log.e("ClientPlayerViewModel", "Error while registering player")
            }
        }
    }

    // every 10 secs call list players
    private fun streamPlayerState() {
        viewModelScope.launch {
            while (true) {
                try {
                    stub.getPlayers(Empty.getDefaultInstance()).collect {
                        Log.d("ClientPlayerViewModel", "Received player: ${it.username}")
                        if (it.id == flowPlayerState.value.id.toString()) {
                            val updatedPlayer = flowPlayerState.value.copy(
                                role = it.role.toRole(),
                                status = it.status.toPlayerStatus(),
                            )
                            _flowPlayerState.update { updatedPlayer }
                            Log.d(
                                "ClientPlayerViewModel",
                                "Updated player: ${flowPlayerState.value}"
                            )
                        }
                    }
                } catch (e: Exception) {
                    // TODO: handle exception
                    e.printStackTrace()
                    Log.e("ClientPlayerViewModel", "Error while streaming player state")
                }
                delay(PLAYER_POLLING_DELAY)
            }
        }
    }
}