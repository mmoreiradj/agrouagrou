package fr.agrouagrou.common.service

import com.google.protobuf.Empty
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.PlayerManager
import fr.agrouagrou.common.player.PlayerRegistry
import fr.agrouagrou.proto.PlayerGrpcKt
import fr.agrouagrou.proto.PlayerNotification
import fr.agrouagrou.proto.PlayerNotificationKt.playerRegistered
import fr.agrouagrou.proto.PlayerNotificationKt.playerUnregistered
import fr.agrouagrou.proto.PlayerRegisterRequest
import fr.agrouagrou.proto.PlayerReply
import fr.agrouagrou.proto.PlayerUnregisterRequest
import fr.agrouagrou.proto.playerNotification
import fr.agrouagrou.proto.playerReply
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
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

    override fun streamPlayerNotifications(request: Empty): Flow<PlayerNotification> =
        flow {
            playerManager.notifications.collect {
                emit(
                    when (it) {
                        is PlayerRegistry.Notification.PlayerRegistered ->
                            playerNotification {
                                playerRegistered =
                                    playerRegistered {
                                        player = it.player.toProto()
                                    }
                            }
                        is PlayerRegistry.Notification.PlayerUnregistered ->
                            playerNotification {
                                playerUnregistered =
                                    playerUnregistered {
                                        id = it.id.toString()
                                    }
                            }
                    },
                )
            }
        }

    private fun Player.toProto(): PlayerReply =
        playerReply {
            id = this@toProto.id.toString()
            username = this@toProto.username
            alive = this@toProto.alive
        }
}
