package fr.agrouagrou.common.player

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.*

class LocalPlayerRegistry : PlayerRegistry {
    private val _players = mutableMapOf<UUID, Player>()
    override val players = _players as Map<UUID, Player>

    private val _notifications = MutableSharedFlow<PlayerRegistry.Notification>()
    override val notifications get() = _notifications.asSharedFlow()

    override suspend fun register(username: String): Player {
        val player = Player(username)
        _players[player.id] = player

        _notifications.emit(PlayerRegistry.Notification.PlayerRegistered(player))
        return player
    }

    override suspend fun unregister(id: UUID) {
        val player = _players.remove(id)

        if (player != null) {
            _notifications.emit(PlayerRegistry.Notification.PlayerUnregistered(id))
        }
    }

    override fun getPlayerStatus(id: UUID): PlayerStatus = getPlayer(id).status

    override fun setPlayerStatus(
        id: UUID,
        status: PlayerStatus,
    ) {
        getPlayer(id).status = status
    }

    override fun finishOffDyingPlayers() {
        _players.values
            .filter { it.status == PlayerStatus.DYING }
            .forEach { it.status = PlayerStatus.DEAD }
    }

    override fun getAlivePlayerCount(): Int = _players.values.count { it.status == PlayerStatus.ALIVE }

    private fun getPlayer(id: UUID): Player = _players[id] ?: throw IllegalArgumentException("Player $id not found")
}
