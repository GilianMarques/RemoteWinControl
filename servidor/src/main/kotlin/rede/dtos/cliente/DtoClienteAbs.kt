package rede.dtos.cliente

import rede.dtos.cliente.TIPO_DTO_CLIENTE

abstract class DtoClienteAbs {

    abstract val tipo: TIPO_DTO_CLIENTE
    val ipParaResposta: String = ""
    val portaParaResposta: Int = -1

    abstract fun toJson(): String

}