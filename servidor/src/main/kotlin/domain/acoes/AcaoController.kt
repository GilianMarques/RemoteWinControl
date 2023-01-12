package domain.acoes

import domain.acoes.reprodutores.Reprodutor
import globalScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import rede.JsonMapper
import rede.dtos.cliente.DtoClienteSemMetadata
import rede.dtos.servidor.DtoServidorAcaoGravada
import rede.io.RedeController

object AcaoController {

    private lateinit var acaoGravada: Acao
    private var gravador: Gravador? = null

    fun gravar() {
        gravador = Gravador()
        gravador!!.gravar()
    }


    fun pararGravacao(data: DtoClienteSemMetadata) {
        desligarListeners()
        println("solicitando solicitacao de envio de açao gravada")
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

    private fun enviarGravacao(data: DtoClienteSemMetadata) = globalScope.launch(IO) {
        println("solicitando envio de açao gravada")
        RedeController.enviar(data.ipParaResposta, data.portaParaResposta, DtoServidorAcaoGravada(acaoGravada))

    }

    fun reproduzir(acao: Acao) {
        // TODO: aguardar o fim da execucao antes de executar outra acao 
        Reprodutor.executar(acao)
    }


}