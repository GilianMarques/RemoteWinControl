package gmarques.remotewincontrol.rede

import com.google.gson.GsonBuilder
import gmarques.remotewincontrol.rede.dtos.ComandoDto
import gmarques.remotewincontrol.rede.io.Cliente
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * Converte os comandos do usuario em objetos que podem ser enviados pro servidor
 * via socket
 * @see Cliente
 * */
class RedeAdapter() {

    private val gson = GsonBuilder()/*.setPrettyPrinting()*/.create()

    suspend fun enviarGesto(comandoDto: ComandoDto): Boolean = withContext(IO) {
        val sGesto = gson.toJson(comandoDto)
        return@withContext Cliente.enviarMsg(sGesto)
    }

    suspend fun manipularVolume(volume: ComandoDto) = withContext(IO) {
        val sGesto = gson.toJson(volume)
        return@withContext Cliente.enviarMsg(sGesto)    }
}