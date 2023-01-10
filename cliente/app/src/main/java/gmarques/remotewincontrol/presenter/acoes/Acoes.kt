package gmarques.remotewincontrol.presenter.acoes

import gmarques.remotewincontrol.rede.dtos.cliente.TIPO_DTO_CLIENTE
import gmarques.remotewincontrol.rede.io.RedeAdapter
import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteSemMetadata
import gmarques.remotewincontrol.rede.dtos.servidor.DtoServidorAcaoGravada
import gmarques.remotewincontrol.rede.dtos.servidor.TIPO_DTO_SERVIDOR
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class Acoes(private val callback: (String) -> Unit) {

    init {
        addListenerDeRede()
    }

    /**
     * Registra um listener que sera notificado quando o servidor enviar
     * o script de acoes gravadas no PC
     * */
    private fun addListenerDeRede() {
        // TODO: servidor fecha antes de enviar a gravaçao
        RedeAdapter.addListener(TIPO_DTO_SERVIDOR.ACAO_GRAVADA) {
            val dto = it as DtoServidorAcaoGravada
            callback.invoke(dto.script)
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


    }
}