package rede.io

import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.Socket


object Cliente {

    suspend fun ligar() = withContext(Dispatchers.IO) {

        val portNumber = 1777
        var str: String? = "Mensagem #1"

        val mSocket = Socket(InetAddress.getLocalHost(), portNumber)
        val mBufferedReader = BufferedReader(InputStreamReader(mSocket.getInputStream())) // le retorno do servidor
        val mPrintWriter = PrintWriter(mSocket.getOutputStream(), true) // escreve pro servidor

        mPrintWriter.println(str) // mando pro servidor

        //aguardo retorno
        while (mBufferedReader.readLine().also { str = it } != null) {
            println("Servidor diz: $str")
            mPrintWriter.println("sair")
            if (str == "sair") break
        }
        mBufferedReader.close()
        mPrintWriter.close()
        mSocket.close()
    }

}