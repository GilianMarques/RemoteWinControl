package gmarques.remotewincontrol.rede.io

import android.util.Log
import gmarques.remotewincontrol.domain.JsonMapper
import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteAbs
import gmarques.remotewincontrol.rede.dtos.servidor.DtoServidorAcaoGravada
import gmarques.remotewincontrol.rede.dtos.servidor.TIPO_DTO_SERVIDOR
import gmarques.remotewincontrol.rede.dtos.servidor.TIPO_DTO_SERVIDOR.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

/**
 *Hub que centraliza as comunicaçoes
 * */
object RedeController {

    private val cliente = Cliente()
    private val servidor = Servidor()
    private val job = Job()
    private val listeners = HashMap<TIPO_DTO_SERVIDOR, ArrayList<RedeCallback>>()

    init {
        CoroutineScope(job).launch(IO) {
            servidor.addListener(::eventoRecebido)
            servidor.ligar()
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

        // Aqui, desserializo o json usando uma das subclasses Dto apenas para acessar o tipo
        // Com essa info posso desserializar usando a classe correta posteriormente
        val comando = JsonMapper.fromJson(entrada, DtoServidorAcaoGravada::class.java)

        Log.d("USUK", "RedeController.eventoRecebido: $entrada")

        when (comando.tipo) {
            ACAO_GRAVADA -> notificarListeners(comando.tipo, entrada)
        }

    }

    private fun notificarListeners(tipo: TIPO_DTO_SERVIDOR, entrada: String) {

        val remocoes = arrayListOf<RedeCallback>()

        listeners[tipo]?.forEach {
            val removerListener = it.eventoRecebido(entrada)
            if (removerListener) remocoes.add(it)
        }

        remocoes.forEach {
            listeners[tipo]?.remove(it)
        }

    }

    fun addListener(tipo: TIPO_DTO_SERVIDOR, callback: RedeCallback) {
        if (listeners[tipo] == null) listeners[tipo] = arrayListOf(callback)
        else listeners[tipo]!!.add(callback)
    }

    fun interface RedeCallback {
        /**
         * retorne true quando quiser remover o listener
         * @param comandoJson o evento recebido do servidor
         * */
        fun eventoRecebido(comandoJson: String): Boolean
    }
}