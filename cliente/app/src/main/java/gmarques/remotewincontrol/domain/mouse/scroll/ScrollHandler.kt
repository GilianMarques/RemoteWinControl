package gmarques.remotewincontrol.domain.mouse.scroll

import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import gmarques.remotewincontrol.rede.io.RedeController

/**
 * Aplica as regras do scroll
 */
object ScrollHandler {

    @Suppress("UnnecessaryVariable")
    fun getMetadata(direcao: Int): Int {

        val direcaoInvertida = direcao * -1
        val linhasRoladas = direcaoInvertida.coerceIn(-1, 1)

        return linhasRoladas

    }

    suspend fun enviarEvento(direcao: Int): Boolean = RedeController.enviar(
        DtoCliente(TIPO_EVENTO_CLIENTE.MOUSE_SCROLL)
            .addInt("direcao", getMetadata(direcao)))

}