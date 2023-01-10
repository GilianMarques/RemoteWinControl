package gmarques.remotewincontrol.rede.dtos.cliente

import com.google.gson.Gson

@Suppress("unused")
class DtoClienteSemMetadata(override val tipo: TIPO_DTO_CLIENTE) : DtoClienteAbs() {


    override fun toJson(): String = Gson().toJson(this)


}