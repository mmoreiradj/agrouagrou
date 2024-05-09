package fr.agrouagrou.common.player

import kotlinx.coroutines.flow.SharedFlow
import java.util.*

interface PlayerRegistry {
    val players: Map<UUID, Player>
    val notifications: SharedFlow<Notification>

    suspend fun register(username: String): Player

    suspend fun unregister(id: UUID)

    sealed class Notification {
        data class PlayerRegistered(val player: Player) : Notification()

        data class PlayerUnregistered(val id: UUID) : Notification()
    }
}
