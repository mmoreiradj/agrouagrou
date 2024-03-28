package fr.agrouagrou.common.player

import java.util.UUID

data class Player(
    val username: String,
    val id: UUID = UUID.randomUUID(),
    val state: State = State.ALIVE,
    val role: Role? = null,
)
