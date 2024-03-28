package fr.agrouagrou

import fr.agrouagrou.common.GameManager
import fr.agrouagrou.service.GameStateService
import fr.agrouagrou.service.PlayerService
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService

class DedicatedServer(private val port: Int) {
    private val gameManager = GameManager()

    private val server: Server =
        ServerBuilder
            .forPort(port)
            .addService(GameStateService(gameManager))
            .addService(PlayerService(gameManager.playerManager))
            .addService(ProtoReflectionService.newInstance())
            .intercept(GrpcExceptionInterceptor())
            .build()

    fun run() {
        Runtime.getRuntime().addShutdownHook(Thread {
            println("Shutting down the server")
            server.shutdown()
        })

        server.start()
        println("Server started, listening on $port")
        server.awaitTermination()
    }
}
