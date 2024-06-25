package fr.agrouagrou.common.extensions

import fr.agrouagrou.common.player.Role
import fr.agrouagrou.proto.PlayerRole

fun PlayerRole.toRole(): Role =
    when (this) {
        PlayerRole.VILLAGER -> Role.Villager
        PlayerRole.WEREWOLF -> Role.Werewolf
        PlayerRole.FORTUNE_TELLER -> Role.FortuneTeller
        PlayerRole.WITCH -> Role.Witch()
        PlayerRole.UNRECOGNIZED -> throw IllegalArgumentException("Unknown role: $this")
    }
