package gmarques.remotewincontrol.domain.acoes

import java.util.ArrayList
import java.util.UUID

data class Acao(val id: String = UUID.randomUUID().toString()) {
    var nome: String = ""
    val etapas: ArrayList<Etapa> = ArrayList()
}