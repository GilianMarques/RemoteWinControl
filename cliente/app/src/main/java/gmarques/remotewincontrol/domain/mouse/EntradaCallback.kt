package gmarques.remotewincontrol.domain.mouse

import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente

fun interface EntradaCallback {

    fun entradaValidada(entrada: DtoCliente)

}