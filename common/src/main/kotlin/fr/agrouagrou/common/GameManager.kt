package fr.agrouagrou.common

import fr.agrouagrou.common.player.LocalPlayerRegistry
import fr.agrouagrou.common.player.PlayerManager
import kotlinx.coroutines.flow.MutableStateFlow

class GameManager(private val rules: GameRules) {
    var gameState: MutableStateFlow<GameState> = MutableStateFlow(GameState.LOOKING_FOR_PLAYERS)
    val playerManager = PlayerManager(LocalPlayerRegistry(), gameState)

    fun startGame() {
        if (gameState.value != GameState.LOOKING_FOR_PLAYERS) {
            throw IllegalStateException("Game is already started")
        } else if (playerManager.players.size < rules.minPlayers) {
            throw IllegalStateException("Not enough players to start the game")
        }

        gameState.value = GameState.STARTING_GAME
    }
}
