package fr.agrouagrou.common.turn

import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.player.PlayerManager
import fr.agrouagrou.common.player.PlayerStatus
import fr.agrouagrou.common.player.Role
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class WerewolfActions(
    private val gameState: MutableStateFlow<GameState>,
    private val playerManager: PlayerManager,
    private val werewolfCount: Int,
) : SpecificRoleActions<Role.Werewolf>(Role.Werewolf::class, GameState.NIGHT_WEREWOLF) {
    private val votes = mutableMapOf<UUID, UUID>()

    fun voteVictim(
        playerId: String,
        targetId: String,
    ) {
        val player =
            playerManager.players[UUID.fromString(playerId)]
                ?: throw IllegalArgumentException("Player not found")

        validateAction(player, gameState.value)

        val target =
            playerManager.players[UUID.fromString(targetId)]
                ?: throw IllegalArgumentException("Target not found")

        votes[player.id] = target.id
    }

    override fun nextGameState() {
        if (votes.size < werewolfCount) {
            throw IllegalStateException("Not all werewolves have voted")
        }

        val mostVotedPlayer =
            votes.values
                .groupingBy { it }
                .eachCount()
                .maxByOrNull { it.value }
                ?.key ?: throw IllegalStateException(
                "No votes",
            )

        playerManager.setPlayerStatus(mostVotedPlayer, PlayerStatus.DYING)
        gameState.value = GameState.NIGHT_WITCH
    }
}
