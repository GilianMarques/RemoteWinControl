package gmarques.remotewincontrol.rede.dtos.servidor

import com.google.gson.Gson


class DtoServidorAcaoGravada() : DtoServidorAbs() {
    var script = ""

    override val tipo: TIPO_DTO_SERVIDOR = TIPO_DTO_SERVIDOR.ACAO_GRAVADA

    override fun toJson(): String = Gson().toJson(this)

}