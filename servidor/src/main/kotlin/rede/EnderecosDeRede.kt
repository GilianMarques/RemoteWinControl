package rede

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket

class EnderecosDeRede {
    companion object {

         suspend fun lerIpDaRede(): String = withContext(IO) {
            return@withContext try {
                val socket = Socket()
                socket.connect(InetSocketAddress("google.com", 80))
                socket.localAddress.hostAddress ?: "_null"
            } catch (e: Exception) {
                e.printStackTrace()
                "null_"
            }
        }




    }

}