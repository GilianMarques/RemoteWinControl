package gmarques.remotewincontrol.rede.dtos

import gmarques.remotewincontrol.presenter.ComandosUsuario
import gmarques.remotewincontrol.rede.EnderecosDeRede

class ComandoDto(
    val tipo: ComandosUsuario,
    @Suppress("unused") vararg val metadata: Float,
    val ipParaResposta: String = EnderecosDeRede.ip,
    val portaParaResposta: Int = EnderecosDeRede.portaEntrada,

    )