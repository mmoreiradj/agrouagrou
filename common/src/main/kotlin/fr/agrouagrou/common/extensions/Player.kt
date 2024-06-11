package fr.agrouagrou.common.extensions

import fr.agrouagrou.common.player.Player
import fr.agrouagrou.common.player.PlayerStatus
import fr.agrouagrou.common.player.Role
import fr.agrouagrou.proto.PlayerReply
import fr.agrouagrou.proto.PlayerRole
import fr.agrouagrou.proto.playerReply
import fr.agrouagrou.proto.PlayerStatus as PlayerStatusProto

fun Player.toProto(): PlayerReply =
    playerReply {
        id = this@toProto.id.toString()
        username = this@toProto.username
        status = this@toProto.status.toProto()
        role = this@toProto.role.toProto()
    }

fun PlayerStatus.toProto(): PlayerStatusProto =
    when (this) {
        PlayerStatus.ALIVE -> PlayerStatusProto.ALIVE
        PlayerStatus.DYING -> PlayerStatusProto.DYING
        PlayerStatus.DEAD -> PlayerStatusProto.DEAD
    }

fun Role.toProto(): PlayerRole =
    when (this) {
        Role.Villager -> PlayerRole.VILLAGER
        Role.Werewolf -> PlayerRole.WEREWOLF
        Role.FortuneTeller -> PlayerRole.FORTUNE_TELLER
        is Role.Witch -> PlayerRole.WITCH
    }
