package fr.agrouagrou.mobileapp.network

import io.grpc.ManagedChannel
import fr.agrouagrou.proto.helloworld.GreeterGrpcKt
import fr.agrouagrou.proto.helloworld.helloRequest
import java.io.Closeable
import java.util.concurrent.TimeUnit

class HelloWorldClient(private val channel: ManagedChannel) : Closeable {
    private val stub: GreeterGrpcKt.GreeterCoroutineStub =
        GreeterGrpcKt.GreeterCoroutineStub(channel)

    suspend fun add(lhs: Int, rhs: Int): Int {
        val request = helloRequest {
            this.lhs = lhs
            this.rhs = rhs
        }

        return stub.sayHello(request).result
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}