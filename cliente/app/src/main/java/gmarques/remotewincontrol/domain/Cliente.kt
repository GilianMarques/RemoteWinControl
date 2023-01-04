package gmarques.remotewincontrol.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.Socket


object Cliente {


    suspend fun ligar() = withContext(Dispatchers.IO) {

        val portNumber = 1777
        var str = "initialize"

        val mSocket = Socket(InetAddress.getLocalHost(), portNumber)
        val br = BufferedReader(InputStreamReader(mSocket.getInputStream()))
        val pw = PrintWriter(mSocket.getOutputStream(), true)

        pw.println(str)
        println(str)

        while (br.readLine().also { str = it } != null) {
            println(str)
            pw.println("bye")
            if (str == "bye") break
        }
        br.close()
        pw.close()
        mSocket.close()
    }

}