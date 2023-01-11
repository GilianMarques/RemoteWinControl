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

        var portaDoServidor = -1
        var portaDoCliente = -1
        var ipDoServidor = ""
        var ipDoCliente = ""

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

            portaDoServidor = Preferencias().getInt(PREFS_PORTA, REDE_PORTA_PADRAO)
            ipDoServidor = Preferencias().getString(PREFS_IP, lerIpDaRede())!!

            portaDoCliente = portaDoServidor + 1
            ipDoCliente = lerIpDaRede()

            Log.d("USUK", "Rede.atualizarEnderecos porta: $portaDoServidor ip $ipDoServidor ")
        }


    }

}