package gmarques.remotewincontrol.presenter.acoes

import gmarques.remotewincontrol.data.AcoesDao
import gmarques.remotewincontrol.rede.JsonMapper
import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteSemMetadata
import gmarques.remotewincontrol.rede.dtos.cliente.TIPO_DTO_CLIENTE
import gmarques.remotewincontrol.rede.dtos.servidor.DtoServidorAcaoGravada
import gmarques.remotewincontrol.rede.dtos.servidor.TIPO_DTO_SERVIDOR
import gmarques.remotewincontrol.rede.io.RedeAdapter
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

// TODO: essa classe deveria se chamar acoes controller?
class Acoes(private val callback: () -> Unit) {

    private lateinit var dto: DtoServidorAcaoGravada

    init {
        addListenerDeRede()
    }

    /**
     * Registra um listener que sera notificado quando o servidor enviar
     * o script de acoes gravadas no PC
     * */
    private fun addListenerDeRede() {
        RedeAdapter.addListener(TIPO_DTO_SERVIDOR.ACAO_GRAVADA) { json ->

            dto = JsonMapper.fromJson(json, DtoServidorAcaoGravada::class.java)
            callback.invoke()
            return@addListener true
        }
    }

    suspend fun iniciarGravacao() = withContext(IO) {
        RedeAdapter.enviar(DtoClienteSemMetadata(TIPO_DTO_CLIENTE.ACAO_GRAVAR))

    }

    suspend fun pararGravacao() = withContext(IO) {
        RedeAdapter.enviar(DtoClienteSemMetadata(TIPO_DTO_CLIENTE.ACAO_PARAR_GRAVACAO))

    }

    fun salvarAcao(nome: String) {
        val acao = Acao(nome, dto.script)
        AcoesDao().salvarAcao(acao)
    }


}