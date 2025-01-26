package dev.gmarques.remotewincontrol.domain.funcoes.mouse

import dev.gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente

fun interface EntradaCallback {

    fun entradaValidada(entrada: DtoCliente)

}