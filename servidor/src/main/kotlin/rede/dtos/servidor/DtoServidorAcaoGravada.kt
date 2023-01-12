package rede.dtos.servidor

import domain.acoes.Acao
import rede.JsonMapper

class DtoServidorAcaoGravada(acao: Acao) : DtoServidorAbs() {
    var acao = ""

    init {
        this.acao = JsonMapper.toJson(acao)
    }

    override val tipo: TIPO_DTO_SERVIDOR = TIPO_DTO_SERVIDOR.ACAO_GRAVADA

    override fun toJson(): String = JsonMapper.toJson(this)

}