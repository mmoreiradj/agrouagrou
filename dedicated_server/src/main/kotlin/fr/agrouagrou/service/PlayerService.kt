package fr.agrouagrou.service

import com.google.protobuf.Empty
import fr.agrouagrou.common.player.PlayerManager
import fr.agrouagrou.proto.PlayerGrpcKt
import fr.agrouagrou.proto.PlayerRegisterRequest
import fr.agrouagrou.proto.PlayerUnregisterRequest
import fr.agrouagrou.proto.playerRegisterReply
import java.util.*

class PlayerService(private val playerManager: PlayerManager) : PlayerGrpcKt.PlayerCoroutineImplBase() {
    override suspend fun register(request: PlayerRegisterRequest) =
        playerRegisterReply {
            val player = playerManager.register(request.username)
            println("Registering new player: $player")

            id = player.id.toString()
            username = player.username
            alive = player.state
        }

    override suspend fun unregister(request: PlayerUnregisterRequest): Empty {
        playerManager.unregister(UUID.fromString(request.id))
        println("Unregistering player: ${request.id}")

        return Empty.getDefaultInstance()
    }
}
