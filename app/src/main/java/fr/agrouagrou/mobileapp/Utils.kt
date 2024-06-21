package fr.agrouagrou.mobileapp

import fr.agrouagrou.common.player.Role
import fr.agrouagrou.grpc_server.R

fun roleToImage(role: Role?): Int = when (role) {
    Role.Villager -> R.drawable.simple_villager
    Role.Werewolf -> R.drawable.simple_werewolf
    is Role.Witch -> R.drawable.witch
    Role.FortuneTeller -> R.drawable.fortune_teller
    null -> R.drawable.card_back
}
