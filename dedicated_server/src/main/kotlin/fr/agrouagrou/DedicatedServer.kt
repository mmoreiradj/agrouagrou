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
            .addService(GameStateService(gameManager.gameState))
            .addService(PlayerService(gameManager.playerManager))
            .addService(ProtoReflectionService.newInstance())
            .build()

    fun run() {
        server.start()
        println("Server started, listening on $port")
        server.awaitTermination()
    }
}
