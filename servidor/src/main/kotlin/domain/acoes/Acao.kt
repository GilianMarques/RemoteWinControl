package domain.acoes

import rede.JsonMapper
import java.util.*

data class Acao(val id: String = UUID.randomUUID().toString()) {
    var nome: String = ""
    val etapas: ArrayList<Etapa> = ArrayList()

    fun toJson() = JsonMapper.toJson(this)

}
