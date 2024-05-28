package fr.agrouagrou.common

import fr.agrouagrou.common.player.PlayerManager
import fr.agrouagrou.common.state.FortuneTellerActions
import fr.agrouagrou.common.state.GameState
import kotlinx.coroutines.flow.MutableStateFlow

class GameTurn(val turnNumber: Int, gameState: MutableStateFlow<GameState>, playerManager: PlayerManager) {
    val fortuneTellerActions = FortuneTellerActions(gameState, playerManager)
}
