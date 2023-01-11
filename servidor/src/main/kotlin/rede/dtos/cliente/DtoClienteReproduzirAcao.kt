package rede.dtos.cliente

import rede.JsonMapper


@Suppress("unused")
class DtoClienteReproduzirAcao(val script: String) : DtoClienteAbs() {

    override val tipo: TIPO_DTO_CLIENTE = TIPO_DTO_CLIENTE.ACAO_REPRODUZIR_GRAVACAO

    override fun toJson(): String = JsonMapper.toJson(this)

}