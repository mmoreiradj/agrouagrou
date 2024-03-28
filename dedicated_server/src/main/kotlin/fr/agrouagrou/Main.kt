package fr.agrouagrou

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class DedicatedServerCli : CliktCommand() {
    private val port by option(help = "Port to listen on").int().default(50051)

    override fun run() {
        val dedicatedServer = DedicatedServer(port)
        dedicatedServer.run()
    }
}

fun main(args: Array<String>) {
    DedicatedServerCli().main(args)
}
