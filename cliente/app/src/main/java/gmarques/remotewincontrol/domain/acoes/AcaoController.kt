package gmarques.remotewincontrol.domain.acoes

import gmarques.remotewincontrol.data.AcoesDao
import gmarques.remotewincontrol.domain.JsonMapper
import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteSemMetadata
import gmarques.remotewincontrol.rede.dtos.cliente.TIPO_DTO_CLIENTE
import gmarques.remotewincontrol.rede.dtos.servidor.DtoServidorAcaoGravada
import gmarques.remotewincontrol.rede.dtos.servidor.TIPO_DTO_SERVIDOR
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
        RedeController.addListener(TIPO_DTO_SERVIDOR.ACAO_GRAVADA) { json ->

            val dto = JsonMapper.fromJson(json, DtoServidorAcaoGravada::class.java)
            acaoGravada = JsonMapper.fromJson(dto.acao, Acao::class.java)

            callback.invoke()
            return@addListener true
        }
    }

    suspend fun iniciarGravacao() = withContext(IO) {
        RedeController.enviar(DtoClienteSemMetadata(TIPO_DTO_CLIENTE.ACAO_GRAVAR))

    }

    suspend fun pararGravacao() = withContext(IO) {
        RedeController.enviar(DtoClienteSemMetadata(TIPO_DTO_CLIENTE.ACAO_PARAR_GRAVACAO))

    }

    suspend fun abortarGravacao() = withContext(IO) {
        RedeController.enviar(DtoClienteSemMetadata(TIPO_DTO_CLIENTE.ACAO_ABORTAR_GRAVACAO))

    }

    fun salvarAcao(nome: String) {
        acaoGravada.nome = nome
        AcoesDao().salvarAcao(acaoGravada)
    }


}