package fr.agrouagrou.common.turn

import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.Role
import kotlin.reflect.KClass

abstract class SpecificRoleActions<T : Role>(
    private val role: KClass<T>,
    validState: GameState,
) : RoleActions(validState) {
    override fun validateAction(
        player: Player,
        gameState: GameState,
    ) {
        super.validateAction(player, gameState)

        if (player.role::class != role) {
            throw IllegalArgumentException("Player does not have the required role")
        }
    }
}
