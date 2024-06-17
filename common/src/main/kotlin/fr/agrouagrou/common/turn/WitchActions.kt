package fr.agrouagrou.common.turn

import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.PlayerManager
import fr.agrouagrou.common.player.PlayerStatus
import fr.agrouagrou.common.player.Role
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class WitchActions(
    private val gameState: MutableStateFlow<GameState>,
    private val playerManager: PlayerManager,
) : SpecificRoleActions<Role.Witch>(Role.Witch::class, GameState.NIGHT_WITCH) {
    private var hasUsedPotion = false

    fun killPlayer(
        playerId: String,
        targetId: String,
    ) {
        val (player, targetStatus) = prepareUsingPotion(playerId, targetId)

        if (targetStatus != PlayerStatus.ALIVE) {
            throw IllegalArgumentException("Target is not alive")
        }

        (player.role as Role.Witch).useDeathPotion()
        playerManager.setPlayerStatus(UUID.fromString(targetId), PlayerStatus.DYING)
        hasUsedPotion = true
    }

    fun revivePlayer(
        playerId: String,
        targetId: String,
    ) {
        val (player, targetStatus) = prepareUsingPotion(playerId, targetId)

        if (targetStatus != PlayerStatus.DYING) {
            throw IllegalArgumentException("Only a dying player can be revived")
        }

        (player.role as Role.Witch).useLifePotion()
        playerManager.setPlayerStatus(UUID.fromString(targetId), PlayerStatus.ALIVE)
        hasUsedPotion = true
    }

    private fun prepareUsingPotion(
        playerId: String,
        targetId: String,
    ): Pair<Player, PlayerStatus> {
        val player =
            playerManager.players[UUID.fromString(playerId)]
                ?: throw IllegalArgumentException("Player not found")

        validateAction(player, gameState.value)

        if (hasUsedPotion) {
            throw IllegalStateException("Witch has already used her potion")
        }

        val playerStatus = playerManager.getPlayerStatus(UUID.fromString(targetId))

        return Pair(player, playerStatus)
    }

    override fun nextGameState() {
        playerManager.finishOffDyingPlayers()
        gameState.value = GameState.DAY_DEBATE
    }
}
