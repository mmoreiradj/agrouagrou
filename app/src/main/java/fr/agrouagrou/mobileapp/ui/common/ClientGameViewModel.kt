package fr.agrouagrou.mobileapp.ui.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.protobuf.Empty
import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.extensions.toGameState
import fr.agrouagrou.proto.GameStateGrpcKt
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClientGameViewModel(channel: ManagedChannel) : ViewModel() {
    private val _flowGameState = MutableStateFlow(GameState.LOOKING_FOR_PLAYERS)
    val flowGameState: StateFlow<GameState> = _flowGameState

    private val stub = GameStateGrpcKt.GameStateCoroutineStub(channel)

    init {
        startStreaming()
    }

    private fun startStreaming() {
        viewModelScope.launch {
            val request = Empty.getDefaultInstance()
            try {
                stub.streamGameState(request).collect {
                    Log.d("ClientGameViewModel", "Received game state: ${it.status}")
                    _flowGameState.value = it.status.toGameState()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ClientGameViewModel", "Error while streaming game state")
            }
        }
    }
}