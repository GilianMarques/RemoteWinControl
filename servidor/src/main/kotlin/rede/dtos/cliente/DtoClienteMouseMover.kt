package rede.dtos.cliente

import com.google.gson.Gson

@Suppress("unused")
class DtoClienteMouseMover(val movX: Float, val movY: Float) : DtoClienteAbs() {

    override val tipo: TIPO_DTO_CLIENTE = TIPO_DTO_CLIENTE.PAD_MOVE

    override fun toJson(): String = Gson().toJson(this)

}