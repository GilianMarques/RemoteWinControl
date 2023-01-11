package rede.dtos.cliente

import rede.JsonMapper

@Suppress("unused")
class DtoClienteSemMetadata(override val tipo: TIPO_DTO_CLIENTE) : DtoClienteAbs() {


    override fun toJson(): String = JsonMapper.toJson(this)


}