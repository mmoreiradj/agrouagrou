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
        GameState.DAY_DEBATE -> GameStateStatus.DAY_DEBATE
        GameState.DAY_VOTE -> GameStateStatus.DAY_VOTE
    }

fun GameStateStatus.toGameState() =
    when (this) {
        GameStateStatus.LOOKING_FOR_PLAYERS -> GameState.LOOKING_FOR_PLAYERS
        GameStateStatus.NIGHT_START -> GameState.NIGHT_START
        GameStateStatus.NIGHT_FORTUNE_TELLER -> GameState.NIGHT_FORTUNE_TELLER
        GameStateStatus.NIGHT_WEREWOLF -> GameState.NIGHT_WEREWOLF
        GameStateStatus.NIGHT_WITCH -> GameState.NIGHT_WITCH
        GameStateStatus.DAY_DEBATE -> GameState.DAY_DEBATE
        GameStateStatus.DAY_VOTE -> GameState.DAY_VOTE
        GameStateStatus.UNRECOGNIZED -> throw IllegalArgumentException("Unrecognized game state")
    }
