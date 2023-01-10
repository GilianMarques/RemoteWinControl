package rede.dtos.cliente

import com.google.gson.Gson

@Suppress("unused")
class DtoClienteMouseRolar(val dir: Int) : DtoClienteAbs() {

    override val tipo: TIPO_DTO_CLIENTE = TIPO_DTO_CLIENTE.SCROLL

    override fun toJson(): String = Gson().toJson(this)

}