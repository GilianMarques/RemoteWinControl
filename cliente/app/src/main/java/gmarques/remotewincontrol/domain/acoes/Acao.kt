package gmarques.remotewincontrol.domain.acoes

import gmarques.remotewincontrol.domain.JsonMapper
import java.util.ArrayList
import java.util.UUID

@Suppress("unused")
data class Acao(val id: String = UUID.randomUUID().toString()) {
    val etapas: ArrayList<Etapa> = ArrayList()
    var nome: String = ""
        set(value) {
            field = value
                .trim()
                .capitalize()
        }

    fun toJson() = JsonMapper.toJson(this)

}