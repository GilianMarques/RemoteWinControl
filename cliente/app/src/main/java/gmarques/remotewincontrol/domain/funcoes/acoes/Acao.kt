package gmarques.remotewincontrol.domain.funcoes.acoes

import gmarques.remotewincontrol.domain.JsonMapper
import java.util.ArrayList
import java.util.UUID

@Suppress("unused")
data class Acao(val id: String = UUID.randomUUID().toString()) {

    companion object {
        const val velocidadeMinima = 0.25f
        const val velocidadeMaxima = 10f
    }

    val etapas: ArrayList<Etapa> = ArrayList()
    var velocidade: Float = 1f
    var posicao: Int = 1
    var nome: String = ""
        set(value) {
            field = value
                .trim()
                .capitalize()
        }

    fun toJson() = JsonMapper.toJson(this)

}