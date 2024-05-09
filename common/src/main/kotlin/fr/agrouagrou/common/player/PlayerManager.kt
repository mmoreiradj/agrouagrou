package fr.agrouagrou.common.player

import fr.agrouagrou.common.state.GameState
import fr.agrouagrou.common.state.GameStateStatus
import java.util.*

class PlayerManager(private val registry: PlayerRegistry, private val gameState: GameState) : PlayerRegistry by registry {
    override suspend fun register(username: String): Player {
        if (gameState.status.value != GameStateStatus.LOOKING_FOR_PLAYERS) {
            throw IllegalStateException("Cannot register player when game is not looking for players")
        }

        return registry.register(username)
    }

    override suspend fun unregister(id: UUID) {
        if (gameState.status.value != GameStateStatus.LOOKING_FOR_PLAYERS) {
            throw IllegalStateException("Cannot unregister player when game is not looking for players")
        }

        registry.unregister(id)
    }
}
