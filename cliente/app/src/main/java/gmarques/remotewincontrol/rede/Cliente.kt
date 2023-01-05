package gmarques.remotewincontrol.rede

import android.util.Log
import gmarques.remotewincontrol.data.IP
import gmarques.remotewincontrol.data.PORTA
import gmarques.remotewincontrol.data.Preferencias
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.Socket


object Cliente : ICliente {
    private var porta = -1
    private var ip = InetAddress.getLocalHost().toString()

    init {
        porta = Preferencias().getInt(PORTA, 1777)
        ip = Preferencias().getString(IP, ip)!!

        Log.d("USUK", "Cliente.init porta: $porta ip $ip ")


    }


    override suspend fun enviarMsg(mensagem: String) = withContext(Dispatchers.IO) {

        val mSocket = Socket(ip, porta)

        PrintWriter(mSocket.getOutputStream(), true)
            .also {
                it.print(mensagem)
                it.close()
            }

        mSocket.close()

    }

}