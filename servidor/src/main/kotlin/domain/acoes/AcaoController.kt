package domain.acoes

import domain.acoes.reprodutores.Reprodutor
import globalScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import domain.dtos.cliente.DtoCliente
import domain.dtos.servidor.DtoServidor
import gmarques.remotewincontrol.domain.dtos.servidor.TIPO_EVENTO_SERVIDOR
import rede.JsonMapper
import rede.io.RedeController

object AcaoController {

    private lateinit var acaoGravada: Acao
    private var gravador: Gravador? = null

    fun gravar(comando: DtoCliente) {
        gravador = Gravador(comando.getInt("ignorar_movimento_mouse") == 1)
        gravador!!.gravar()
    }


    fun pararGravacao(data: DtoCliente) {
        desligarListeners()
        enviarGravacao(data)
    }

    fun abortarGravacao() {
        desligarListeners()

    }

    private fun desligarListeners() {
        if (gravador == null) return
        acaoGravada = gravador!!.pararGravacao()
        gravador = null
    }

    private fun enviarGravacao(data: DtoCliente) = globalScope.launch(IO) {
        RedeController.enviar(
            data.ipResposta,
            data.portaResposta,
            DtoServidor(TIPO_EVENTO_SERVIDOR.ACAO_GRAVADA)
                .addString("acao", acaoGravada.toJson())
        )
    }

    // TODO: aguardar o fim da execucao antes de executar outra acao
    fun reproduzir(comando: DtoCliente) {
        val acao = JsonMapper.fromJson(comando.getString("acao"), Acao::class.java)
        Reprodutor.reproduzir(acao, comando)
    }

    fun pararReproducao() = Reprodutor.cancelar()


}