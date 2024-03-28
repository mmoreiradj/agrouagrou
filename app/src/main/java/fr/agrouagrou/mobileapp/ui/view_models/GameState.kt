package fr.agrouagrou.mobileapp.ui.view_models

enum class GameTime {
    WAITING_FOR_PLAYERS,
    DAY,
    NIGHT,
    VOTE
}

// Do not move in proto file
enum class Role {
    GM,
    NONE,
    VILLAGER,
    WEREWOLF
}

// TODO: Maybe move in proto file
enum class PlayerState {
    ALIVE,
    DEAD
}

// TODO: Maybe move in proto file
data class Player(
    val id: String,
    val name: String,
    val role: Role,
    val state: PlayerState
)

data class GameState(
    val time: GameTime = GameTime.WAITING_FOR_PLAYERS,
    val players: List<Player> = emptyList(),
    val me: Player = Player("1", "Me", Role.NONE, PlayerState.ALIVE),
)

class GameViewModel {}
