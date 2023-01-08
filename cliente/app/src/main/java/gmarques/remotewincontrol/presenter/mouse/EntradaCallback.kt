package gmarques.remotewincontrol.presenter.mouse

import gmarques.remotewincontrol.rede.dtos.ComandoDto

fun interface EntradaCallback {

    fun entradaValidada(comandoDto: ComandoDto)

}