package fr.agrouagrou.common.player

import java.util.*

data class Player(
    val username: String,
    val id: UUID = UUID.randomUUID(),
    var status: PlayerStatus = PlayerStatus.ALIVE,
    var role: Role = Role.Villager,
)
