package fr.agrouagrou.common.player

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.*

class PlayerRegistry {
    private val mutablePlayers = mutableMapOf<UUID, Player>()
    val players = mutablePlayers as Map<UUID, Player>

    private val _notifications = MutableSharedFlow<Notification>()
    val notifications get() = _notifications.asSharedFlow()

    suspend fun register(username: String): Player {
        val player = Player(username)
        mutablePlayers[player.id] = player

        _notifications.emit(Notification.PlayerRegistered(player))
        return player
    }

    suspend fun unregister(id: UUID) {
        val player = mutablePlayers.remove(id)

        if (player != null) {
            _notifications.emit(Notification.PlayerUnregistered(id))
        }
    }

    sealed class Notification {
        data class PlayerRegistered(val player: Player) : Notification()

        data class PlayerUnregistered(val id: UUID) : Notification()
    }
}
