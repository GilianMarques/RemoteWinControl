package comandos_remotos.acoes

import com.google.gson.Gson
import rede.RedeAdapter
import rede.dtos.ComandoDto

object Acoes {

    private val gravador = Gravador()

    fun gravar() {

        gravador.gravar()
    }

    // TODO: afazeres:
    //  salvar posicao relativa de movimento do mouse
    //  enviar comando para o telefone

    fun pararGravacao(metadata: ComandoDto) {
        val acao = gravador.pararGravacao()

    }

    fun pararGravacaoTest() = gravador.pararGravacao()


}