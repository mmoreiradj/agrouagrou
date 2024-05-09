package fr.agrouagrou.common.service

import com.google.protobuf.Empty
import fr.agrouagrou.common.GameManager
import fr.agrouagrou.common.GameState
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
            status = gameManager.gameState.value.toProto()
        }

    override fun streamGameState(request: Empty): Flow<StreamGameStatusReply> =
        flow {
            gameManager.gameState.asStateFlow().collect {
                emit(
                    streamGameStatusReply {
                        status = it.toProto()
                    },
                )
            }
        }

    private fun GameState.toProto() =
        when (this) {
            GameState.LOOKING_FOR_PLAYERS -> GameStateStatusProto.LOOKING_FOR_PLAYERS
            GameState.NIGHT_START -> GameStateStatusProto.NIGHT_START
            GameState.NIGHT_FORTUNE_TELLER -> GameStateStatusProto.NIGHT_FORTUNE_TELLER
            GameState.NIGHT_WEREWOLF -> GameStateStatusProto.NIGHT_WEREWOLF
            GameState.NIGHT_WITCH -> GameStateStatusProto.NIGHT_WITCH
        }
}
