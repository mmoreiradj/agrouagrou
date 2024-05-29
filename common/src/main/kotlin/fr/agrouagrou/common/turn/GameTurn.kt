package fr.agrouagrou.common.turn

import fr.agrouagrou.common.GameState
import fr.agrouagrou.common.player.PlayerManager
import kotlinx.coroutines.flow.MutableStateFlow

class GameTurn(val turnNumber: Int, gameState: MutableStateFlow<GameState>, playerManager: PlayerManager) {
    val fortuneTellerActions = FortuneTellerActions(gameState, playerManager)
    val werewolfActions = WerewolfActions(gameState, playerManager)
    val witchActions = WitchActions(gameState, playerManager)
}
