package rede.dtos

import ComandosUsuario

class ComandoDto(
    val tipo: ComandosUsuario,
    vararg val metadata: Float,
    val ipParaResposta: String,
    val portaParaResposta: Int,
)