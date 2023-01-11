package rede.dtos.servidor

import comandos_remotos.acoes.Etapa
import rede.JsonMapper

class DtoServidorAcaoGravada(etapas: ArrayList<Etapa>) : DtoServidorAbs() {
    var script = ""

    init {
        script = JsonMapper.toJson(etapas)
    }

    override val tipo: TIPO_DTO_SERVIDOR = TIPO_DTO_SERVIDOR.ACAO_GRAVADA

    override fun toJson(): String = JsonMapper.toJson(this)

}