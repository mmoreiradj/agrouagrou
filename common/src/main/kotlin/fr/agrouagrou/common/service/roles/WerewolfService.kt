package fr.agrouagrou.common.service.roles

import com.google.protobuf.Empty
import fr.agrouagrou.common.GameManager
import fr.agrouagrou.proto.VoteWerewolfVictimRequest
import fr.agrouagrou.proto.WerewolfActionsGrpcKt

class WerewolfService(
    private val gameManager: GameManager,
) : WerewolfActionsGrpcKt.WerewolfActionsCoroutineImplBase() {
    override suspend fun voteVictim(request: VoteWerewolfVictimRequest): Empty {
        gameManager.currentTurn().werewolfActions.voteVictim(request.playerId, request.targetId)
        return Empty.getDefaultInstance()
    }
}
