package dev.gmarques.remotewincontrol.domain.network.io

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.BindException
import java.net.ServerSocket

//http://www.java2s.com/Code/Java/Network-Protocol/StringbasedcommunicationbetweenSocket.htm

class DataReceiver {

    private lateinit var listener: (String) -> Any

    @SuppressWarnings("kotlin:S6310")
    suspend fun executar() = withContext(Dispatchers.IO) {

        try {
            val porta = EnderecosDeRede.portaDoCliente
            val server = ServerSocket(porta)
            Log.d(
                "USUK",
                "DataReceiver.executar: Conectado via porta: $porta e ip ${EnderecosDeRede.ipDoCliente}"
            )

            while (true) {
                val cliente = server.accept()
                val br = BufferedReader(InputStreamReader(cliente.getInputStream()))

                val entrada = br.readLine()
                withContext(Main) { listener.invoke(entrada) }

                br.close()
                cliente.close()
            }

        } catch (ex: BindException) { // porta em uso
            Log.d("USUK", "DataReceiver.executar: ${ex.message}")
        }
    }

    fun definirListener(listener: (String) -> Any) {
        this.listener = listener
    }
}