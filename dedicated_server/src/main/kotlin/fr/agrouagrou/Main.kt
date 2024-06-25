package fr.agrouagrou

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import fr.agrouagrou.common.GameRules

class DedicatedServerCli : CliktCommand() {
    private val port by option(help = "Port to listen on").int().default(50051)
    private val minPlayers by option(help = "Minimum number of players to start the game").int().default(5)
    private val werewolfCount by option(help = "Number of werewolves in the game").int().default(2)

    override fun run() {
        val gameRules = GameRules(minPlayers, werewolfCount)
        val dedicatedServer = DedicatedServer(gameRules, port)
        dedicatedServer.run()
    }
}

fun main(args: Array<String>) {
    DedicatedServerCli().main(args)
}
