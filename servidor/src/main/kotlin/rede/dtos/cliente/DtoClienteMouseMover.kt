package rede.dtos.cliente

import rede.JsonMapper

@Suppress("unused")
class DtoClienteMouseMover(val movX: Float, val movY: Float) : DtoClienteAbs() {

    override val tipo: TIPO_DTO_CLIENTE = TIPO_DTO_CLIENTE.PAD_MOVE

    override fun toJson(): String = JsonMapper.toJson(this)

}