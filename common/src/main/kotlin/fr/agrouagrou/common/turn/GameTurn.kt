package fr.agrouagrou.common.turn

import fr.agrouagrou.common.GameRules
import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.player.PlayerManager
import kotlinx.coroutines.flow.MutableStateFlow

class GameTurn(
    val turnNumber: Int,
    gameState: MutableStateFlow<GameState>,
    playerManager: PlayerManager,
    rules: GameRules,
) {
    val fortuneTellerActions = FortuneTellerActions(gameState, playerManager)
    val werewolfActions = WerewolfActions(gameState, playerManager, rules.werewolfCount)
    val witchActions = WitchActions(gameState, playerManager)
    val villagerActions = VillagerActions(gameState, playerManager)
}
