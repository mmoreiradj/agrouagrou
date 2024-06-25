package fr.agrouagrou.mobileapp.ui.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.proto.PlayerGrpcKt
import fr.agrouagrou.proto.PlayerRegisterRequest
import io.grpc.ManagedChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ClientPlayerViewModel(channel: ManagedChannel, username: String) : ViewModel() {
    private val _flowPlayerState: MutableStateFlow<Player> = MutableStateFlow(Player(username))
    val flowPlayerState: StateFlow<Player> = _flowPlayerState

    private val stub = PlayerGrpcKt.PlayerCoroutineStub(channel)

    fun connectToServer() {
        viewModelScope.launch {
            try {
                val response = stub.register(
                    PlayerRegisterRequest
                        .newBuilder()
                        .setUsername(flowPlayerState.value.username)
                        .build()
                )
                val updatedPlayer = _flowPlayerState.value.copy(id = UUID.fromString(response.id))
                _flowPlayerState.update { updatedPlayer }
                Log.d("ClientPlayerViewModel", "Registered player with id: ${flowPlayerState.value.id}")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ClientPlayerViewModel", "Error while registering player")
            }
        }
    }
}