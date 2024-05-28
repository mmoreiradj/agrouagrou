package fr.agrouagrou.common.extensions

import fr.agrouagrou.common.GameState
import fr.agrouagrou.proto.GameStateStatus

fun GameState.toProto() =
    when (this) {
        GameState.LOOKING_FOR_PLAYERS -> GameStateStatus.LOOKING_FOR_PLAYERS
        GameState.NIGHT_START -> GameStateStatus.NIGHT_START
        GameState.NIGHT_FORTUNE_TELLER -> GameStateStatus.NIGHT_FORTUNE_TELLER
        GameState.NIGHT_WEREWOLF -> GameStateStatus.NIGHT_WEREWOLF
        GameState.NIGHT_WITCH -> GameStateStatus.NIGHT_WITCH
    }
