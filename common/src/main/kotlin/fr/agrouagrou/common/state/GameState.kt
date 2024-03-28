package fr.agrouagrou.common.state

data class GameState(
    val status: GameStateStatus = GameStateStatus.LOOKING_FOR_PLAYERS,
)
