package dev.gmarques.remotewincontrol.domain.network.io

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.PrintWriter
import java.net.Socket

//http://www.java2s.com/Code/Java/Network-Protocol/StringbasedcommunicationbetweenSocket.htm

/**
 * Usado pelo ControladorDeRede para enviar mensagens ao servidor
 * Nao use essa classe diretamente.
 * @See RedeController
 * */
class DataSender {


    /**
     * Envia uma mensagem para o servidor via socket
     * @return true se a mensagem foi enviada com sucesso
     */
    suspend fun enviarMsg(mensagem: String): Boolean = withContext(Dispatchers.IO) {
        try {

            val mSocket = Socket(EnderecosDeRede.ipDoServidor, EnderecosDeRede.portaDoServidor)
            mSocket.tcpNoDelay = true
            PrintWriter(mSocket.getOutputStream(), true)
                .also {
                    it.println(mensagem) // mando pro servidor
                    it.close()
                }

            //      Log.d("USUK", "Cliente.enviarMsg: \"$mensagem\" ")
            mSocket.close()

            return@withContext true
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext false
        }

    }

}