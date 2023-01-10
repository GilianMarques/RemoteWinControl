package rede.dtos.servidor

import com.google.gson.Gson
import comandos_remotos.acoes.Etapa

class DtoServidorAcaoGravada(etapas: ArrayList<Etapa>) : DtoServidorAbs() {
    var script = ""

    init {
        script = Gson().toJson(etapas)
    }

    override val tipo: TIPO_DTO_SERVIDOR = TIPO_DTO_SERVIDOR.ACAO_GRAVADA

    override fun toJson(): String = Gson().toJson(this)

}