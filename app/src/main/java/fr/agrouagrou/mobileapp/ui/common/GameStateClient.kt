package fr.agrouagrou.mobileapp.ui.common

import com.google.protobuf.Empty
import fr.agrouagrou.proto.GameStateGrpcKt
import fr.agrouagrou.proto.StreamGameStatusReply
import io.grpc.ManagedChannel
import kotlinx.coroutines.flow.Flow
import java.io.Closeable

class GameStateClient(private val channel: ManagedChannel) : Closeable {
    private val stub: GameStateGrpcKt.GameStateCoroutineStub = GameStateGrpcKt.GameStateCoroutineStub(channel)

    fun streamGameState(): Flow<StreamGameStatusReply> {
        return stub.streamGameState(Empty.getDefaultInstance())
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)
    }
}