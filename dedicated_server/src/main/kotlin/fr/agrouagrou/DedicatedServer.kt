package fr.agrouagrou

import fr.agrouagrou.common.GameManager
import fr.agrouagrou.common.GameRules
import fr.agrouagrou.common.service.DebugService
import fr.agrouagrou.common.service.GameStateService
import fr.agrouagrou.common.service.PlayerService
import fr.agrouagrou.common.service.roles.FortuneTellerService
import fr.agrouagrou.common.service.roles.WerewolfService
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService

class DedicatedServer(gameRules: GameRules, private val port: Int) {
    private val gameManager = GameManager(gameRules)

    private val server: Server =
        ServerBuilder
            .forPort(port)
            .addService(GameStateService(gameManager))
            .addService(PlayerService(gameManager.playerManager))
            .addService(DebugService(gameManager))
            .addService(FortuneTellerService(gameManager))
            .addService(WerewolfService(gameManager))
            .addService(ProtoReflectionService.newInstance())
            .intercept(GrpcExceptionInterceptor())
            .build()

    fun run() {
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("Shutting down the server")
                server.shutdown()
            },
        )

        server.start()
        println("Server started, listening on $port")
        server.awaitTermination()
    }
}
