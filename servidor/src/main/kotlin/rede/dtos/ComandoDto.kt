package rede.dtos

import EntradaUsuario

 class ComandoDto(
    val tipo: EntradaUsuario,
    vararg val metadata: Float,
)