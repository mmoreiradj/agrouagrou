package fr.agrouagrou.common

import fr.agrouagrou.common.player.LocalPlayerRegistry
import fr.agrouagrou.common.player.PlayerManager
import fr.agrouagrou.common.turn.GameTurn
import kotlinx.coroutines.flow.MutableStateFlow

class GameManager(
    private val rules: GameRules,
) {
    var gameState: MutableStateFlow<GameState> = MutableStateFlow(GameState.LOOKING_FOR_PLAYERS)
    val playerManager = PlayerManager(LocalPlayerRegistry(), gameState, rules)

    private val turns = ArrayDeque<GameTurn>()

    fun startGame() {
        if (gameState.value != GameState.LOOKING_FOR_PLAYERS) {
            throw IllegalStateException("Game is already started")
        } else if (playerManager.players.size < rules.minPlayers) {
            throw IllegalStateException("Not enough players to start the game")
        }

        playerManager.assignRoles()
        nextTurn()
    }

    fun currentTurn(): GameTurn {
        val currentTurn = turns.lastOrNull() ?: throw IllegalStateException("Game has not started yet")
        return currentTurn
    }

    private fun nextTurn() {
        val turnNumber = turns.lastOrNull()?.turnNumber ?: 0
        turns.add(GameTurn(turnNumber + 1, gameState, playerManager, rules))

        gameState.value = GameState.NIGHT_START
    }

    fun nextGameState() {
        when (gameState.value) {
            GameState.LOOKING_FOR_PLAYERS -> startGame()
            GameState.NIGHT_START -> gameState.value = GameState.NIGHT_FORTUNE_TELLER
            GameState.NIGHT_FORTUNE_TELLER -> currentTurn().fortuneTellerActions.nextGameState()
            GameState.NIGHT_WEREWOLF -> currentTurn().werewolfActions.nextGameState()
            GameState.NIGHT_WITCH -> currentTurn().witchActions.nextGameState()
            GameState.DAY_DEBATE -> gameState.value = GameState.DAY_VOTE
            GameState.DAY_VOTE -> {
                currentTurn().villagerActions.nextGameState()
                nextTurn()
            }
        }
    }
}
