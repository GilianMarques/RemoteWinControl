package gmarques.remotewincontrol.rede.dtos.cliente

import gmarques.remotewincontrol.rede.EnderecosDeRede

abstract class DtoClienteAbs {

    abstract val tipo: TIPO_DTO_CLIENTE
    @Suppress("unused")
    val ipParaResposta: String = EnderecosDeRede.ip
    @Suppress("unused")
    val portaParaResposta: Int = EnderecosDeRede.portaEntrada

    abstract fun toJson(): String

}