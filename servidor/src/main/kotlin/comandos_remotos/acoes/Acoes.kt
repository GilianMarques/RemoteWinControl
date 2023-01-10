package comandos_remotos.acoes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
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


    fun pararGravacao(data: DtoClienteSemMetadata) {
        acaoGravada = gravador!!.pararGravacao()
        gravador = null
        enviarGravacao(data)
    }

    private fun enviarGravacao(data: DtoClienteSemMetadata) = CoroutineScope(IO).launch {
        withTimeout(20/*segs*/ * 1000) {
            RedeAdapter.enviar(data.ipParaResposta, data.portaParaResposta, DtoServidorAcaoGravada(acaoGravada))
        }
    }


    fun pararGravacaoTest() = gravador!!.pararGravacao()


}