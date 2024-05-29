package fr.agrouagrou.common.turn

import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.PlayerManager
import fr.agrouagrou.common.player.Role
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class WitchActions(
    private val gameState: MutableStateFlow<GameState>,
    private val playerManager: PlayerManager,
) : RoleActions<Role.Witch>(Role.Witch::class, GameState.NIGHT_WITCH) {
    private var hasUsedPotion = false

    fun killPlayer(
        playerId: String,
        targetId: String,
    ) {
        val (player, isTargetAlive) = prepareUsingPotion(playerId, targetId)

        if (!isTargetAlive) {
            throw IllegalArgumentException("Target is already dead")
        }

        (player.role as Role.Witch).useDeathPotion()
        playerManager.setPlayerAlive(UUID.fromString(targetId), false)
        hasUsedPotion = true
    }

    fun revivePlayer(
        playerId: String,
        targetId: String,
    ) {
        val (player, isTargetAlive) = prepareUsingPotion(playerId, targetId)

        if (isTargetAlive) {
            throw IllegalArgumentException("Target is already alive")
        }

        (player.role as Role.Witch).useLifePotion()
        playerManager.setPlayerAlive(UUID.fromString(targetId), true)
        hasUsedPotion = true
    }

    private fun prepareUsingPotion(
        playerId: String,
        targetId: String,
    ): Pair<Player, Boolean> {
        val player =
            playerManager.players[UUID.fromString(playerId)]
                ?: throw IllegalArgumentException("Player not found")

        validateAction(player, gameState.value)

        if (hasUsedPotion) {
            throw IllegalStateException("Witch has already used her potion")
        }

        val isTargetAlive = playerManager.isPlayerAlive(UUID.fromString(targetId))

        return Pair(player, isTargetAlive)
    }
}
