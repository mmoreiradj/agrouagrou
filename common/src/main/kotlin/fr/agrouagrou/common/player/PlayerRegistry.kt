package fr.agrouagrou.common.player

import java.util.*

class PlayerRegistry {
    private val mutablePlayers = mutableMapOf<UUID, Player>()
    val players = mutablePlayers as Map<UUID, Player>

    fun register(username: String): Player {
        val player = Player(username)
        mutablePlayers[player.id] = player

        return player
    }

    fun unregister(id: UUID) {
        mutablePlayers.remove(id)
    }
}
