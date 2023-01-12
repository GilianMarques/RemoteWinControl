package gmarques.remotewincontrol.rede.dtos.servidor

import gmarques.remotewincontrol.domain.acoes.Acao
import gmarques.remotewincontrol.domain.JsonMapper


class DtoServidorAcaoGravada(etapas: Acao) : DtoServidorAbs() {
    var acao = ""

    init {
        acao = JsonMapper.toJson(etapas)
    }

    override val tipo: TIPO_DTO_SERVIDOR = TIPO_DTO_SERVIDOR.ACAO_GRAVADA

    override fun toJson(): String = JsonMapper.toJson(this)

}