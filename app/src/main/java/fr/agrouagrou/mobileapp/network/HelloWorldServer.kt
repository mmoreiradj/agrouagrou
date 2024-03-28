package fr.agrouagrou.mobileapp.network

import io.grpc.InsecureServerCredentials
import io.grpc.okhttp.OkHttpServerBuilder
import fr.agrouagrou.proto.helloworld.GreeterGrpcKt
import fr.agrouagrou.proto.helloworld.HelloRequest
import fr.agrouagrou.proto.helloworld.helloReply

class HelloWorldServer(private val port: Int) {
    private val server =
        OkHttpServerBuilder
            .forPort(port, InsecureServerCredentials.create())
            .addService(HelloWorldService())
            .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@HelloWorldServer.stop()
                println("*** server shut down")
            },
        )
    }

    private fun stop() {
        server.shutdown()
    }

    internal class HelloWorldService : GreeterGrpcKt.GreeterCoroutineImplBase() {
        override suspend fun sayHello(request: HelloRequest) =
            helloReply {
                result = request.lhs + request.rhs
            }
    }
}