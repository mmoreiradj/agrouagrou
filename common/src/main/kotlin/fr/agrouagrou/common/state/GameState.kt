package fr.agrouagrou.common.state

data class GameState(
    var status: GameStateStatus = GameStateStatus.LOOKING_FOR_PLAYERS,
)
