package fr.agrouagrou.common.turn

import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.Role
import kotlin.reflect.KClass

abstract class RoleActions<T : Role>(private val role: KClass<T>, private val validState: GameState) {
    fun validateAction(
        player: Player,
        gameState: GameState,
    ) {
        if (player.role::class != role) {
            throw IllegalArgumentException("Player does not have the required role")
        }

        if (gameState != validState) {
            throw IllegalArgumentException("This action is not allowed at this time")
        }
    }

    abstract fun nextGameState()
}
