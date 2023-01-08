package gmarques.remotewincontrol.rede

import android.util.Log
import gmarques.remotewincontrol.data.PREFS_IP
import gmarques.remotewincontrol.data.PREFS_PORTA
import gmarques.remotewincontrol.data.Preferencias
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket

class EnderecosDeRede {
    companion object {

        var porta = -1
        var portaEntrada = -1
        var ip = ""

        private suspend fun lerIpDaRede(): String = withContext(IO) {
            return@withContext try {
                val socket = Socket()
                socket.connect(InetSocketAddress("google.com", 80))
                socket.localAddress.hostAddress ?: "_null"
            } catch (e: Exception) {
                e.printStackTrace()
                "null_"
            }
        }

        suspend fun atualizarEnderecos() {
            porta = Preferencias().getInt(PREFS_PORTA, REDE_PORTA_PADRAO)
            portaEntrada = porta + 1
            ip = Preferencias().getString(PREFS_IP, lerIpDaRede())!!
            Log.d("USUK", "Rede.atualizarEnderecos porta: $porta ip $ip ")
        }


    }

}