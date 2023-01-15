package gmarques.remotewincontrol.rede.io

import android.util.Log
import gmarques.remotewincontrol.domain.JsonMapper
import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import gmarques.remotewincontrol.domain.dtos.servidor.DtoServidor
import gmarques.remotewincontrol.domain.dtos.servidor.TIPO_EVENTO_SERVIDOR
import gmarques.remotewincontrol.domain.dtos.servidor.TIPO_EVENTO_SERVIDOR.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

/**
 *Hub que centraliza as comunicaçoes
 * */
object RedeController {

    private val cliente = Cliente()
    private val servidor = Servidor()
    private val job = Job()
    private val listeners = HashMap<TIPO_EVENTO_SERVIDOR, ArrayList<RedeCallback>>()

    init {
        CoroutineScope(job).launch(IO) {
            servidor.addListener(::eventoRecebido)
            servidor.ligar()
        }
    }

    suspend fun enviar(dto: DtoCliente): Boolean = withContext(IO) {
        dto.ipResposta = EnderecosDeRede.ipDoCliente
        dto.portaResposta = EnderecosDeRede.portaDoCliente

        return@withContext cliente.enviarMsg(dto.toJson())
    }

    /**
     * Chamado a partir do servidor
     * @see Servidor
     * */
    private fun eventoRecebido(entrada: String) {


        val comando = JsonMapper.fromJson(entrada, DtoServidor::class.java)

        Log.d("USUK", "RedeController.eventoRecebido: '${comando.tipo}' $entrada")

        when (comando.tipo) {
            ACAO_GRAVADA -> notificarListeners(comando.tipo, comando)
            else -> {}
        }

    }

    private fun notificarListeners(tipo: TIPO_EVENTO_SERVIDOR, comando: DtoServidor) {

        val remocoes = arrayListOf<RedeCallback>()

        listeners[tipo]?.forEach {
            val removerListener = it.eventoRecebido(comando)
            if (removerListener) remocoes.add(it)
        }

        remocoes.forEach {
            listeners[tipo]?.remove(it)
        }

    }

    fun addListener(tipo: TIPO_EVENTO_SERVIDOR, callback: RedeCallback) {
        if (listeners[tipo] == null) listeners[tipo] = arrayListOf(callback)
        else listeners[tipo]!!.add(callback)
    }

    fun interface RedeCallback {
        /**
         * retorne true quando quiser remover o listener
         * @param comandoJson o evento recebido do servidor
         * */
        fun eventoRecebido(comandoJson: DtoServidor): Boolean
    }
}