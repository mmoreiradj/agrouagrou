package fr.agrouagrou.service

import com.google.protobuf.Empty
import fr.agrouagrou.common.GameManager
import fr.agrouagrou.proto.DebugGrpcKt

class DebugService(private val gameManager: GameManager) : DebugGrpcKt.DebugCoroutineImplBase() {
    override suspend fun startGame(request: Empty): Empty {
        gameManager.startGame()
        return Empty.getDefaultInstance()
    }
}
