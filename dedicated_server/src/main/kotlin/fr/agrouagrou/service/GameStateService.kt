package fr.agrouagrou.service

import com.google.protobuf.Empty
import fr.agrouagrou.common.state.GameState
import fr.agrouagrou.common.state.GameStateStatus
import fr.agrouagrou.proto.GameStateGrpcKt
import fr.agrouagrou.proto.getGameStateReply
import fr.agrouagrou.proto.GameStateStatus as GameStateStatusProto

class GameStateService(private val gameState: GameState) : GameStateGrpcKt.GameStateCoroutineImplBase() {
    override suspend fun getGameState(request: Empty) =
        getGameStateReply {
            status = when (gameState.status) {
                GameStateStatus.LOOKING_FOR_PLAYERS -> GameStateStatusProto.LOOKING_FOR_PLAYERS
            }
        }
}
