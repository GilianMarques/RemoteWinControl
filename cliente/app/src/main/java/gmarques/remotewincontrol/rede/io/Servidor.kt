package gmarques.remotewincontrol.rede.io

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

//http://www.java2s.com/Code/Java/Network-Protocol/StringbasedcommunicationbetweenSocket.htm

object Servidor {


    suspend fun ligar() = withContext(Dispatchers.IO) {

        val porta = 1777
        var mensagem: String?

        val server = ServerSocket(porta)
        println("Tentando conectar pela $porta...")

        val client = server.accept()
        val mPrintWriter = PrintWriter(client.getOutputStream(), true)

        val br = BufferedReader(InputStreamReader(client.getInputStream()))
        println("Conectado pela  porta: $porta")

        while (br.readLine().also { mensagem = it } != null) {
            println("The message: $mensagem")
            if (mensagem == "bye") {
                mPrintWriter.println("bye")
                break
            } else {
                mensagem = "Server returns $mensagem"
                mPrintWriter.println(mensagem)
            }
        }
        mPrintWriter.close()
        br.close()

        client.close()
    }
}