package fr.agrouagrou.common

import fr.agrouagrou.common.player.PlayerManager
import fr.agrouagrou.common.state.GameState
import fr.agrouagrou.common.state.GameStateStatus

class GameManager(private val minPlayers: Int) {
    val gameState = GameState()
    val playerManager = PlayerManager(gameState)

    fun startGame() {
        if (gameState.status.value != GameStateStatus.LOOKING_FOR_PLAYERS) {
            throw IllegalStateException("Game is already started")
        } else if (playerManager.players.size < minPlayers) {
            throw IllegalStateException("Not enough players to start the game")
        }

        gameState.status.value = GameStateStatus.STARTING_GAME
    }
}
