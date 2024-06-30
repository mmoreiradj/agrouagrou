package fr.agrouagrou.mobileapp.ui.common.extensions

import fr.agrouagrou.common.GameState
import fr.agrouagrou.grpc_server.R

fun GameState.toLabel() =
    when (this) {
        GameState.LOOKING_FOR_PLAYERS -> R.string.gamestate_looking_for_players
        GameState.NIGHT_START -> R.string.gamestate_night_start
        GameState.NIGHT_FORTUNE_TELLER -> R.string.gamestate_night_fortune_teller
        GameState.NIGHT_WEREWOLF -> R.string.gamestate_night_werewolf
        GameState.NIGHT_WITCH -> R.string.gamestate_night_witch
        GameState.DAY_DEBATE -> R.string.gamestate_day_debate
        GameState.DAY_VOTE -> R.string.gamestate_day_vote
    }
