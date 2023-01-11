package gmarques.remotewincontrol.presenter.acoes

import java.util.UUID

data class Acao(
    var nome: String,
    val etapas: String,
    val id: String = UUID.randomUUID().toString(),
) {
}