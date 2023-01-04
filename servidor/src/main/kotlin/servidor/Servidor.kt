package servidor

import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket


object Servidor {

    suspend fun ligar() = withContext(Dispatchers.IO) {

        val porta = 1777
        var mensagem: String

        println("Tentando conectar pela porta: $porta")
        val server = ServerSocket(porta)

        val client = server.accept()
        val mPrintWriter = PrintWriter(client.getOutputStream(), true) // usado pra enviar msg pelo socket

        val br = BufferedReader(InputStreamReader(client.getInputStream())) // usado pra ler a entrada recebida no socket
        println("Entrada recebida do cliente")

        while (br.readLine().also { mensagem = it } != null) {
            println("Cliente diz: $mensagem")
            if (mensagem == "sair") {
                mPrintWriter.println("Saindo...")
                break
            } else {
                mensagem = "Mensagem recebida"
                mPrintWriter.println(mensagem)
            }
        }

        mPrintWriter.close()
        br.close()

        client.close()
    }
}