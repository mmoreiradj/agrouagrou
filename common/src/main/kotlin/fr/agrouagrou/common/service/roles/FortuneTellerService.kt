package fr.agrouagrou.common.service.roles

import fr.agrouagrou.common.GameManager
import fr.agrouagrou.common.extensions.toProto
import fr.agrouagrou.proto.FortuneTellerActionsGrpcKt
import fr.agrouagrou.proto.RevealRoleReply
import fr.agrouagrou.proto.RevealRoleRequest
import fr.agrouagrou.proto.revealRoleReply

class FortuneTellerService(
    private val gameManager: GameManager,
) : FortuneTellerActionsGrpcKt.FortuneTellerActionsCoroutineImplBase() {
    override suspend fun revealRole(request: RevealRoleRequest): RevealRoleReply =
        revealRoleReply {
            role =
                gameManager
                    .currentTurn()
                    .fortuneTellerActions
                    .revealRole(request.playerId, request.targetId)
                    .toProto()
        }
}
