package gmarques.remotewincontrol.domain.funcoes.acoes

import gmarques.remotewincontrol.data.AcoesDao
import gmarques.remotewincontrol.domain.JsonMapper
import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import gmarques.remotewincontrol.domain.dtos.servidor.TIPO_EVENTO_SERVIDOR
import gmarques.remotewincontrol.rede.io.RedeController
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class AcaoController(private val callback: () -> Unit) {

    private lateinit var acaoGravada: Acao

    init {
        addListenerDeRede()
    }

    /**
     * Registra um listener que sera notificado quando o servidor enviar
     * o script de acoes gravadas no PC
     * */
    private fun addListenerDeRede() {
        RedeController.addListener(TIPO_EVENTO_SERVIDOR.ACAO_GRAVADA) { comando ->

            val acao = JsonMapper.fromJson(comando.getString("acao"), Acao::class.java)
            acaoGravada = acao
            callback.invoke()
            return@addListener true
        }
    }

    suspend fun iniciarGravacao(checked: Boolean) = withContext(IO) {
        RedeController.enviar(DtoCliente(TIPO_EVENTO_CLIENTE.ACAO_GRAVAR)
            .addInt("ignorar_movimento_mouse", if (checked) 1 else 0))

    }

    suspend fun pararGravacao() = withContext(IO) {
        RedeController.enviar(DtoCliente(TIPO_EVENTO_CLIENTE.ACAO_PARAR_GRAVACAO))
    }

    suspend fun abortarGravacao() = withContext(IO) {
        RedeController.enviar(DtoCliente(TIPO_EVENTO_CLIENTE.ACAO_ABORTAR_GRAVACAO))

    }

    fun salvarAcao(nome: String) {
        acaoGravada.nome = nome
        AcoesDao().salvarAcao(acaoGravada)
    }

}