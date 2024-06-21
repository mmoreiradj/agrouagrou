package fr.agrouagrou.common.service.roles

import com.google.protobuf.Empty
import fr.agrouagrou.common.GameManager
import fr.agrouagrou.proto.VillagerActionsGrpcKt
import fr.agrouagrou.proto.VoteVillagerVictimRequest

class VillagerService(
    private val gameManager: GameManager,
) : VillagerActionsGrpcKt.VillagerActionsCoroutineImplBase() {
    override suspend fun voteVictim(request: VoteVillagerVictimRequest): Empty {
        gameManager.currentTurn().villagerActions.voteVictim(request.playerId, request.targetId)
        return Empty.getDefaultInstance()
    }
}
