package fr.agrouagrou.common.player

import java.util.*

data class Player(val username: String, val id: UUID = UUID.randomUUID(), val alive: Boolean = true)
