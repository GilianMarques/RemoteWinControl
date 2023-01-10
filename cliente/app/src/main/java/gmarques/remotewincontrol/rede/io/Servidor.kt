package gmarques.remotewincontrol.rede.io

import gmarques.remotewincontrol.rede.EnderecosDeRede
import gmarques.remotewincontrol.rede.REDE_PORTA_PADRAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.BindException
import java.net.ServerSocket

//http://www.java2s.com/Code/Java/Network-Protocol/StringbasedcommunicationbetweenSocket.htm

class Servidor {

    private lateinit var listener: (String) -> Any
    private var porta = EnderecosDeRede.portaEntrada

    suspend fun ligar() = withContext(Dispatchers.IO) {

        try {

            val server = ServerSocket(porta)
            println("Conectado via porta: $porta e ip ${EnderecosDeRede.ip}")

            while (true) {

                val cliente = server.accept()
                val br = BufferedReader(InputStreamReader(cliente.getInputStream()))

                val entrada = br.readLine()
                launch { listener.invoke(entrada) }

                br.close()
                cliente.close()
            }

        } catch (ex: BindException) { // porta em uso
            throw ex
        }
    }

     fun addListener(listener: (String) -> Any) {
        this.listener = listener
    }
}