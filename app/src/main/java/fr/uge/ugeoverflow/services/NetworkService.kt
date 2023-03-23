package fr.uge.ugeoverflow.services

import android.util.Log
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException

object NetworkService {

    //This only get the local ip address,
    private fun getLocalIpAddress(): String? {
        try {
            val networkInterfaces = NetworkInterface.getNetworkInterfaces()
            networkInterfaces.iterator().forEach { networkInterface ->
                val inetAddresses = networkInterface.inetAddresses
                inetAddresses.iterator().forEach { inetAddress ->
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        val ip = inetAddress.hostAddress
                        if (ip != null) {
                            Log.e("IP", ip)
                        }
                        return ip
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }
        return null
    }

    fun getBaseURL(): String {
        val ip = getLocalIpAddress()
        return "http://$ip:8080"
    }


}