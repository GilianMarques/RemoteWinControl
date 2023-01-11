package comandos_remotos.acoes

import comandos_remotos.acoes.reprodutores.Reprodutor
import globalScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import rede.JsonMapper
import rede.dtos.cliente.DtoClienteReproduzirAcao
import rede.dtos.cliente.DtoClienteSemMetadata
import rede.dtos.servidor.DtoServidorAcaoGravada
import rede.io.RedeAdapter
import java.util.ArrayList

object Acoes {

    private lateinit var acaoGravada: ArrayList<Etapa>
    private var gravador: Gravador? = null

    fun gravar() {
        gravador = Gravador()
        gravador!!.gravar()
    }
// TODO: criar uma unica classse para as acços com nome id e um array de etapas para cliente e servidor
// TODO:  criar tbm um DTO StringMEtadata pra passar esse tipo de dados
// TODO: deveo usar essa classe 'Acoes' p/ usar a classe 'Reprodutor' p/ reproduzir as acoes, ou eliminar o intermediario?

    fun pararGravacao(data: DtoClienteSemMetadata) {
        acaoGravada = gravador!!.pararGravacao()
        gravador = null
        enviarGravacao(data)
    }

    private fun enviarGravacao(data: DtoClienteSemMetadata) = globalScope.launch(IO) {
        withTimeout(20/*segs*/ * 1000) {
            RedeAdapter.enviar(data.ipParaResposta, data.portaParaResposta, DtoServidorAcaoGravada(acaoGravada))
        }
    }


    fun pararGravacaoTest() = gravador!!.pararGravacao()

    fun reproduzir(acao: DtoClienteReproduzirAcao) {
        val etapas: ArrayList<Etapa> = JsonMapper.fromJson(acao.script, Array<Etapa>::class.java).toList() as ArrayList<Etapa>
        Reprodutor(etapas).executar()
    }


}