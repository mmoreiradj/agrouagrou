package fr.agrouagrou

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class DedicatedServerCli : CliktCommand() {
    private val port by option(help = "Port to listen on").int().default(50051)
    private val minPlayers by option(help = "Minimum number of players to start the game").int().default(8)

    override fun run() {
        val dedicatedServer = DedicatedServer(minPlayers, port)
        dedicatedServer.run()
    }
}

fun main(args: Array<String>) {
    DedicatedServerCli().main(args)
}
