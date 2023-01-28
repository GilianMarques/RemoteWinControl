package gmarques.remotewincontrol.rede.io

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.BindException
import java.net.ServerSocket

//http://www.java2s.com/Code/Java/Network-Protocol/StringbasedcommunicationbetweenSocket.htm

class Servidor {

    private lateinit var listener: (String) -> Any
    private var porta = EnderecosDeRede.portaDoCliente

    suspend fun executar() = withContext(Dispatchers.IO) {

        try {

            val server = ServerSocket(porta)
            Log.d("USUK", "Servidor.executar: Conectado via porta: $porta e ip ${EnderecosDeRede.ipDoCliente}")

            while (true) {
                val cliente = server.accept()
                val br = BufferedReader(InputStreamReader(cliente.getInputStream()))

                val entrada = br.readLine()
                withContext(Main) { listener.invoke(entrada) }

                br.close()
                cliente.close()
            }

        } catch (ex: BindException) { // porta em uso
            throw ex
        }
    }

    fun definirListener(listener: (String) -> Any) {
        this.listener = listener
    }
}