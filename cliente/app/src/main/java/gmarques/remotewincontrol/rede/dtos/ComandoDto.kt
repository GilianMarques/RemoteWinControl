package gmarques.remotewincontrol.rede.dtos

import gmarques.remotewincontrol.presenter.EntradaUsuario

class ComandoDto(
    val tipo: EntradaUsuario,
    @Suppress("unused") vararg val metadata: Float,
)