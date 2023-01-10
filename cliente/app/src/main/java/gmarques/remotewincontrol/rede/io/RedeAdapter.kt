package gmarques.remotewincontrol.rede.io

import android.util.Log
import com.google.gson.GsonBuilder
import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteAbs
import gmarques.remotewincontrol.rede.dtos.cliente.TIPO_DTO_CLIENTE.*
import gmarques.remotewincontrol.rede.dtos.servidor.DtoServidorAbs
import gmarques.remotewincontrol.rede.dtos.servidor.TIPO_DTO_SERVIDOR
import gmarques.remotewincontrol.rede.dtos.servidor.TIPO_DTO_SERVIDOR.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

/**
 *Hub que centraliza as comunicaçoes
 * */
object RedeAdapter {

    private val cliente = Cliente()
    private val servidor = Servidor()
    private val gson = GsonBuilder().create()
    private val job = Job()
    private val listeners = HashMap<TIPO_DTO_SERVIDOR, ArrayList<RedeCallback>>()

    init {
        CoroutineScope(job).launch(IO) {
            servidor.ligar()
            servidor.addListener(RedeAdapter::eventoRecebido)
        }
    }

    suspend fun enviar(dtoClienteAbs: DtoClienteAbs): Boolean = withContext(IO) {
        return@withContext cliente.enviarMsg(dtoClienteAbs.toJson())
    }

    /**
     * Chamado a partir do servidor
     * @see Servidor
     * */
    private fun eventoRecebido(entrada: String) {

        val comando = gson.fromJson(entrada, DtoServidorAbs::class.java)
        Log.d("USUK", "RedeAdapter.eventoRecebido: $entrada")

        when (comando.tipo) {
            ACAO_GRAVADA -> notificarListeners(comando)
        }

    }

    private fun notificarListeners(comando: DtoServidorAbs) {

        val remocoes = arrayListOf<RedeCallback>()

        listeners[comando.tipo]?.forEach {
            val removerListener = it.eventoRecebido(comando)
            if (removerListener) remocoes.add(it)
        }

        remocoes.forEach {
            listeners[comando.tipo]?.remove(it)
        }

    }

    fun addListener(tipo: TIPO_DTO_SERVIDOR, callback: RedeCallback) {
        if (listeners[tipo] == null) listeners[tipo] = arrayListOf(callback)
        else listeners[tipo]!!.add(callback)
    }

    fun interface RedeCallback {
        /**
         * retorne true quando quiser remover o listener
         * @param comando o evento recebido do servidor
         * */
        fun eventoRecebido(comando: DtoServidorAbs): Boolean
    }
}