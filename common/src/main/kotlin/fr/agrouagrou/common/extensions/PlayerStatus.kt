package fr.agrouagrou.common.extensions

import fr.agrouagrou.proto.PlayerStatus

fun PlayerStatus.toPlayerStatus(): fr.agrouagrou.common.player.PlayerStatus =
    when (this) {
        PlayerStatus.ALIVE -> fr.agrouagrou.common.player.PlayerStatus.ALIVE
        PlayerStatus.DEAD -> fr.agrouagrou.common.player.PlayerStatus.DEAD
        PlayerStatus.UNRECOGNIZED -> throw IllegalArgumentException("Unknown player status: $this")
        PlayerStatus.DYING -> fr.agrouagrou.common.player.PlayerStatus.DYING
    }
