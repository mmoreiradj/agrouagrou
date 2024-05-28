package fr.agrouagrou.common.state

import fr.agrouagrou.common.player.PlayerManager
import fr.agrouagrou.common.player.Role
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class FortuneTellerActions(
    private val gameState: MutableStateFlow<GameState>,
    private val playerManager: PlayerManager,
) : RoleActions<Role.FortuneTeller>(Role.FortuneTeller::class, GameState.NIGHT_FORTUNE_TELLER) {
    private var hasRevealed = false

    fun revealRole(
        playerId: String,
        targetId: String,
    ): Role {
        val player =
            playerManager.players[UUID.fromString(playerId)]
                ?: throw IllegalArgumentException("Player not found")

        validateAction(player, gameState.value)

        if (hasRevealed) {
            throw IllegalStateException("Fortune teller has already revealed")
        }

        val target =
            playerManager.players[UUID.fromString(targetId)]
                ?: throw IllegalArgumentException("Target not found")

        hasRevealed = true
        return target.role
    }
}
