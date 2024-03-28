package fr.agrouagrou.mobileapp

import fr.agrouagrou.common.player.Role
import fr.agrouagrou.grpc_server.R

fun roleToImage(role: Role?): Int = when (role) {
    Role.VILLAGER -> R.drawable.simple_villager
    Role.WEREWOLF -> R.drawable.simple_werewolf
    null -> R.drawable.card_back
}
