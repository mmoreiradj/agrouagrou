package fr.agrouagrou.common.service.roles

import com.google.protobuf.Empty
import fr.agrouagrou.common.GameManager
import fr.agrouagrou.proto.KillPlayerRequest
import fr.agrouagrou.proto.RevivePlayerRequest
import fr.agrouagrou.proto.WitchActionsGrpcKt

class WitchService(private val gameManager: GameManager) : WitchActionsGrpcKt.WitchActionsCoroutineImplBase() {
    override suspend fun killPlayer(request: KillPlayerRequest): Empty {
        gameManager.currentTurn().witchActions.killPlayer(request.playerId, request.targetId)
        return Empty.getDefaultInstance()
    }

    override suspend fun revivePlayer(request: RevivePlayerRequest): Empty {
        gameManager.currentTurn().witchActions.revivePlayer(request.playerId, request.targetId)
        return Empty.getDefaultInstance()
    }
}
