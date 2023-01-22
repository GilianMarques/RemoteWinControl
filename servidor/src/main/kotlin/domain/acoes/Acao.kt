package domain.acoes

import rede.JsonMapper
import java.util.*

@Suppress("unused")
data class Acao(val id: String = UUID.randomUUID().toString()) {

    companion object {
        const val velocidadeMinima = 0.25f
        const val velocidadeMaxima = 10f
    }

    val etapas: ArrayList<Etapa> = ArrayList()
    var velocidade: Float = 1f
    var nome: String = ""
        set(value) {
            field = value
                .trim()
                .capitalize()
        }

    fun toJson() = JsonMapper.toJson(this)

}