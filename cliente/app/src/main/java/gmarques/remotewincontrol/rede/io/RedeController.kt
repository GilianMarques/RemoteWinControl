package gmarques.remotewincontrol.rede.io

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gmarques.remotewincontrol.domain.JsonMapper
import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import gmarques.remotewincontrol.domain.dtos.servidor.DtoServidor
import gmarques.remotewincontrol.domain.dtos.servidor.TIPO_EVENTO_SERVIDOR
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.system.measureTimeMillis

/**
 *Hub que centraliza as comunicaçoes
 * */
object RedeController {

    var _ping = MutableLiveData<Long>(0)
    val ping: LiveData<Long> get() = _ping


    private val dataSender = DataSender()
    private val dataReceiver = DataReceiver()
    private val job = Job()
    private val listeners = HashMap<TIPO_EVENTO_SERVIDOR, ArrayList<RedeCallback>>()

    init {
        CoroutineScope(job).launch(IO) {
            dataReceiver.definirListener(::eventoRecebido)
            dataReceiver.executar()
        }
    }

    suspend fun enviar(dto: DtoCliente): Boolean = withContext(IO) {
        var enviado: Boolean
        _ping.postValue(measureTimeMillis {
            dto.ipResposta = EnderecosDeRede.ipDoCliente
            dto.portaResposta = EnderecosDeRede.portaDoCliente
            enviado = dataSender.enviarMsg(dto.toJson())
        })
        return@withContext enviado

    }

    /**
     * Chamado a partir do servidor
     * @see DataReceiver
     * */
    private fun eventoRecebido(entrada: String) {
        val comando = JsonMapper.fromJson(entrada, DtoServidor::class.java)
        notificarListeners(comando.tipo, comando)
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

    /**@return null se nao houver listeners registrados para esse tipo de evento
     * true se o listener foi encontrado e removido, caso contrario, false
     * */
    fun removerListener(tipo: TIPO_EVENTO_SERVIDOR, callback: RedeCallback): Boolean? {
        return if (listeners[tipo] != null) listeners[tipo]!!.remove(callback) else null

    }

    fun interface RedeCallback {
        /**
         * retorne true quando quiser remover o listener
         * @param comandoJson o evento recebido do servidor
         * */
        fun eventoRecebido(comandoJson: DtoServidor): Boolean
    }
}