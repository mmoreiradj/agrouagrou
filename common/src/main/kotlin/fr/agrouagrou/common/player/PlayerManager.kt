package fr.agrouagrou.common.player

import java.util.*

class PlayerManager {
    private val players = mutableMapOf<UUID, Player>()

    fun register(username: String): Player {
        val player = Player(username)
        players[player.id] = player

        return player
    }

    fun unregister(id: UUID) {
        players.remove(id)
    }
}
