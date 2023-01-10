package gmarques.remotewincontrol.presenter.mouse

import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteAbs

fun interface EntradaCallback {

    fun entradaValidada(entrada: DtoClienteAbs)

}