package fr.agrouagrou.common.service

import com.google.protobuf.Empty
import fr.agrouagrou.common.GameManager
import fr.agrouagrou.common.extensions.toGameState
import fr.agrouagrou.common.extensions.toProto
import fr.agrouagrou.proto.DebugGrpcKt
import fr.agrouagrou.proto.SetGameStateReply
import fr.agrouagrou.proto.SetGameStateRequest
import fr.agrouagrou.proto.setGameStateReply

class DebugService(private val gameManager: GameManager) : DebugGrpcKt.DebugCoroutineImplBase() {
    override suspend fun startGame(request: Empty): Empty {
        gameManager.startGame()
        return Empty.getDefaultInstance()
    }

    override suspend fun setGameState(request: SetGameStateRequest): SetGameStateReply {
        gameManager.gameState.value = request.status.toGameState()

        return setGameStateReply {
            status = gameManager.gameState.value.toProto()
        }
    }

    override suspend fun nextGameState(request: Empty): Empty {
        gameManager.nextGameState()
        return Empty.getDefaultInstance()
    }
}
