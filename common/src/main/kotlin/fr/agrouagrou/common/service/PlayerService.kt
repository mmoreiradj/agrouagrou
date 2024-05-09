package fr.agrouagrou.common.service

import com.google.protobuf.Empty
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.PlayerManager
import fr.agrouagrou.proto.PlayerGrpcKt
import fr.agrouagrou.proto.PlayerRegisterRequest
import fr.agrouagrou.proto.PlayerReply
import fr.agrouagrou.proto.PlayerUnregisterRequest
import fr.agrouagrou.proto.playerReply
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.util.*

class PlayerService(private val playerManager: PlayerManager) : PlayerGrpcKt.PlayerCoroutineImplBase() {
    override suspend fun register(request: PlayerRegisterRequest): PlayerReply {
        val player = playerManager.register(request.username)
        println("Registering new player: $player")

        return player.toProto()
    }

    override suspend fun unregister(request: PlayerUnregisterRequest): Empty {
        playerManager.unregister(UUID.fromString(request.id))
        println("Unregistering player: ${request.id}")

        return Empty.getDefaultInstance()
    }

    override fun getPlayers(request: Empty): Flow<PlayerReply> = playerManager.players.values.map { it.toProto() }.asFlow()

    private fun Player.toProto(): PlayerReply =
        playerReply {
            id = this@toProto.id.toString()
            username = this@toProto.username
            alive = this@toProto.alive
        }
}
