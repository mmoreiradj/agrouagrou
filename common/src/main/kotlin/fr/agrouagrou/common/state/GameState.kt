package fr.agrouagrou.common.state

import kotlinx.coroutines.flow.MutableStateFlow

data class GameState(
    var status: MutableStateFlow<GameStateStatus> = MutableStateFlow(GameStateStatus.LOOKING_FOR_PLAYERS),
)
