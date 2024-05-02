package fr.agrouagrou.service

import com.google.protobuf.Empty
import fr.agrouagrou.common.GameManager
import fr.agrouagrou.common.state.GameStateStatus
import fr.agrouagrou.proto.GameStateGrpcKt
import fr.agrouagrou.proto.StreamGameStatusReply
import fr.agrouagrou.proto.getGameStateReply
import fr.agrouagrou.proto.streamGameStatusReply
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import fr.agrouagrou.proto.GameStateStatus as GameStateStatusProto

class GameStateService(private val gameManager: GameManager) : GameStateGrpcKt.GameStateCoroutineImplBase() {
    override suspend fun getGameState(request: Empty) =
        getGameStateReply {
            status =
                when (gameManager.gameState.status.value) {
                    GameStateStatus.LOOKING_FOR_PLAYERS -> GameStateStatusProto.LOOKING_FOR_PLAYERS
                    GameStateStatus.STARTING_GAME -> GameStateStatusProto.STARTING_GAME
                }
        }

    override fun streamGameState(request: Empty): Flow<StreamGameStatusReply> =
        flow {
            gameManager.gameState.status.asStateFlow().collect {
                emit(
                    streamGameStatusReply {
                        status =
                            when (it) {
                                GameStateStatus.LOOKING_FOR_PLAYERS -> GameStateStatusProto.LOOKING_FOR_PLAYERS
                                GameStateStatus.STARTING_GAME -> GameStateStatusProto.STARTING_GAME
                            }
                    },
                )
            }
        }
}
