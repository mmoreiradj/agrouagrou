package fr.agrouagrou.common.player

import fr.agrouagrou.common.GameRules
import fr.agrouagrou.common.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class PlayerManager(
    private val registry: PlayerRegistry,
    private val gameState: MutableStateFlow<GameState>,
    private val rules: GameRules,
) : PlayerRegistry by registry {
    override suspend fun register(username: String): Player {
        if (gameState.value != GameState.LOOKING_FOR_PLAYERS) {
            throw IllegalStateException("Cannot register player when game is not looking for players")
        }

        return registry.register(username)
    }

    override suspend fun unregister(id: UUID) {
        if (gameState.value != GameState.LOOKING_FOR_PLAYERS) {
            throw IllegalStateException("Cannot unregister player when game is not looking for players")
        }

        registry.unregister(id)
    }

    fun assignRoles() {
        if (gameState.value != GameState.LOOKING_FOR_PLAYERS) {
            throw IllegalStateException("Cannot assign roles when game is not looking for players")
        }

        val roles = mutableListOf<Role>()
        repeat(rules.werewolfCount) { roles.add(Role.Werewolf) }
        roles.add(Role.FortuneTeller)
        roles.add(Role.Witch())
        repeat(players.size - roles.size) { roles.add(Role.Villager) }

        players.forEach { (_, player) ->
            val role = roles.random()
            player.role = role
            roles.remove(role)
        }
    }
}
