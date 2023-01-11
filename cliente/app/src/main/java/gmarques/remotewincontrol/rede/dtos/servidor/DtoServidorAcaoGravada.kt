package gmarques.remotewincontrol.rede.dtos.servidor

import com.google.gson.Gson
import gmarques.remotewincontrol.rede.JsonMapper


class DtoServidorAcaoGravada : DtoServidorAbs() {

    var script = ""

    override val tipo: TIPO_DTO_SERVIDOR = TIPO_DTO_SERVIDOR.ACAO_GRAVADA

    override fun toJson(): String = JsonMapper.toJson(this)

}