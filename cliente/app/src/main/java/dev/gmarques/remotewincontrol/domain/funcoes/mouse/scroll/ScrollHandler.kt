package dev.gmarques.remotewincontrol.domain.funcoes.mouse.scroll

import dev.gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import dev.gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import dev.gmarques.remotewincontrol.domain.network.io.RedeController

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