package fr.agrouagrou.mobileapp

import fr.agrouagrou.common.player.Role
import fr.agrouagrou.grpc_server.R
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.Collections

fun roleToImage(role: Role?): Int = when (role) {
    Role.Villager -> R.drawable.simple_villager
    Role.Werewolf -> R.drawable.simple_werewolf
    is Role.Witch -> R.drawable.witch
    Role.FortuneTeller -> R.drawable.fortune_teller
    null -> R.drawable.card_back
}

fun getPrivateIpAddress(): String? {
    return try {
        val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
        for (networkInterface in interfaces) {
            val addresses = Collections.list(networkInterface.inetAddresses)
            for (address in addresses) {
                if (!address.isLoopbackAddress && address is Inet4Address) {
                    return address.hostAddress
                }
            }
        }
        null
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}

object IpPortEncDec {
    fun encodeIpPort(ip: String, port: Int): String {
        val ipParts = ip.split(".").map { it.toInt().toString(16).padStart(2, '0') }
        val ipHex = ipParts.joinToString("")
        val portHex = port.toString(16).padStart(4, '0')
        return "$ipHex-$portHex"
    }

    fun decodeIpPort(encodedString: String): Pair<String, Int>? {
        val parts = encodedString.split("-")
        if (parts.size != 2) return null
        val ipHex = parts[0]
        val portHex = parts[1]

        val ip = listOf(
            ipHex.substring(0, 2).toInt(16),
            ipHex.substring(2, 4).toInt(16),
            ipHex.substring(4, 6).toInt(16),
            ipHex.substring(6, 8).toInt(16)
        ).joinToString(".")

        val port = portHex.toInt(16)
        return Pair(ip, port)
    }
}
