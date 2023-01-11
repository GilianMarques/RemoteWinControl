package gmarques.remotewincontrol.rede.dtos.cliente

import com.google.gson.Gson
import gmarques.remotewincontrol.rede.JsonMapper

@Suppress("unused")
class DtoClienteSemMetadata(override val tipo: TIPO_DTO_CLIENTE) : DtoClienteAbs() {


    override fun toJson(): String = JsonMapper.toJson(this)


}