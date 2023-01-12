package rede.io

import kotlinx.coroutines.*
import rede.REDE_PORTA_PADRAO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.*

//http://www.java2s.com/Code/Java/Network-Protocol/StringbasedcommunicationbetweenSocket.htm


class Servidor {

    private var porta = REDE_PORTA_PADRAO

    suspend fun ligar(listener: (String) -> Unit) = withContext(Dispatchers.IO) {

        try {

            val server = ServerSocket(porta)
            println("Conectado via porta: $porta e ip ${EnderecosDeRede.lerIpDaRede()}")

            while (true) {
                val cliente = server.accept()
                val br = BufferedReader(InputStreamReader(cliente.getInputStream()))

                val entrada = br.readLine()
                listener.invoke(entrada)

                br.close()
                cliente.close()
            }

        } catch (ex: BindException) { // porta em uso
            tratarPortaEmUso(listener)

        }
    }

    private suspend fun tratarPortaEmUso(listener: (String) -> Unit) {
        println("Porta $porta em uso tentando proxima...")
        porta++
        ligar(listener)
    }


}