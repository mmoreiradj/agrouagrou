package fr.agrouagrou.common

import fr.agrouagrou.common.player.PlayerManager
import fr.agrouagrou.common.state.GameState

class GameManager {
    val gameState = GameState()
    val playerManager = PlayerManager(gameState)
}
