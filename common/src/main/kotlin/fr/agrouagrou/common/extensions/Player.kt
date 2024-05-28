package fr.agrouagrou.common.extensions

import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.Role
import fr.agrouagrou.proto.PlayerReply
import fr.agrouagrou.proto.PlayerRole
import fr.agrouagrou.proto.playerReply

fun Player.toProto(): PlayerReply =
    playerReply {
        id = this@toProto.id.toString()
        username = this@toProto.username
        alive = this@toProto.alive
        role = this@toProto.role.toProto()
    }

fun Role.toProto(): PlayerRole =
    when (this) {
        Role.Villager -> PlayerRole.VILLAGER
        Role.Werewolf -> PlayerRole.WEREWOLF
        Role.FortuneTeller -> PlayerRole.FORTUNE_TELLER
        is Role.Witch -> PlayerRole.WITCH
    }
