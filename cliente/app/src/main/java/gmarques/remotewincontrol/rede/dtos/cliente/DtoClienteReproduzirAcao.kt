package gmarques.remotewincontrol.rede.dtos.cliente

import gmarques.remotewincontrol.domain.JsonMapper
import gmarques.remotewincontrol.domain.acoes.Acao

@Suppress("unused")
class DtoClienteReproduzirAcao(acao: Acao) : DtoClienteAbs() {

    val acao: String

    init {
        this.acao = JsonMapper.toJson(acao)
    }

    override val tipo: TIPO_DTO_CLIENTE = TIPO_DTO_CLIENTE.ACAO_REPRODUZIR_GRAVACAO

    override fun toJson(): String = JsonMapper.toJson(this)

}