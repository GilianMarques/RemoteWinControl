package gmarques.remotewincontrol.rede.dtos.cliente

import gmarques.remotewincontrol.domain.JsonMapper

@Suppress("unused")
class DtoClienteMouseRolar(val dir: Int) : DtoClienteAbs() {

    override val tipo: TIPO_DTO_CLIENTE = TIPO_DTO_CLIENTE.SCROLL

    override fun toJson(): String = JsonMapper.toJson(this)

}