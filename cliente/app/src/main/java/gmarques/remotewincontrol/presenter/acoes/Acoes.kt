package gmarques.remotewincontrol.presenter.acoes

import com.google.gson.Gson
import gmarques.remotewincontrol.presenter.ComandosUsuario
import gmarques.remotewincontrol.rede.dtos.ComandoDto
import gmarques.remotewincontrol.rede.io.Cliente
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class Acoes {

    private val gson = Gson()

    suspend fun iniciarGravacao() = withContext(IO) {
        Cliente
            .enviarMsg(gson.toJson(ComandoDto(ComandosUsuario.ACAO_GRAVAR)))
    }


    suspend fun pararGravacao() = withContext(IO) {
        Cliente
            .enviarMsg(gson.toJson(ComandoDto(ComandosUsuario.ACAO_PARAR_GRAVACAO)))
    }

    fun salvarAcao(nome: String) {


    }
}