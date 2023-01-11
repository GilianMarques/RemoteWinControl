package rede.io

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.PrintWriter
import java.net.Socket

//http://www.java2s.com/Code/Java/Network-Protocol/StringbasedcommunicationbetweenSocket.htm

/**
 * Usado pelo para enviar mensagens ao telefone
 * Nao use essa classe diretamente.
 * @See RedeAdapter
 * */
class Cliente {


    /**
     * Envia uma mensagem para o servidor via “socket”
     * @return true se a mensagem foi enviada com sucesso
     * */
    suspend fun enviarMsg(mensagem: String, porta: Int, ip: String): Boolean = withContext(Dispatchers.IO) {
        try {

            val mSocket = Socket(ip, porta)
            PrintWriter(mSocket.getOutputStream(), true)
                .also {
                    it.println(mensagem) // mando p/ servidor
                    it.close()
                }

            println("comando enviado para ip $ip porta $porta msg = \"$mensagem\"")
            mSocket.close()

            return@withContext true
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext false
        }

    }

}