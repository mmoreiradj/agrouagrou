package fr.agrouagrou.common.turn

import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.PlayerStatus

abstract class RoleActions(
    private val validState: GameState,
) {
    open fun validateAction(
        player: Player,
        gameState: GameState,
    ) {
        if (gameState != validState) {
            throw IllegalArgumentException("This action is not allowed at this time")
        }

        if (player.status == PlayerStatus.DEAD) {
            throw IllegalArgumentException("Dead players cannot perform actions")
        }
    }

    abstract fun nextGameState()
}
