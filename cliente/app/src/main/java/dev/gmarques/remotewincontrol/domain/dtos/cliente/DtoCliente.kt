package dev.gmarques.remotewincontrol.domain.dtos.cliente

import dev.gmarques.remotewincontrol.domain.JsonMapper

@Suppress("unused")
/**
 * Estrutura de dados usada para enviar os comandos do cliente (Android) para o servidor (Windows)
 */
data class DtoCliente(val tipo: TIPO_EVENTO_CLIENTE = TIPO_EVENTO_CLIENTE.NONE) {


    private val intMap = HashMap<String, Int>()
    private val floatMap = HashMap<String, Float>()
    private val stringMap = HashMap<String, String>()

    var ipResposta: String = "_null"
    var portaResposta: Int = -1

    companion object {
        fun fromJson(json: String) {
            JsonMapper.fromJson(json, DtoCliente::class.java)
        }
    }

    fun toJson() = JsonMapper.toJson(this)


    fun addInt(chave: String, valor: Int) = apply {
        intMap[chave] = valor
    }

    fun addFloat(chave: String, valor: Float) = apply {
        floatMap[chave] = valor
    }

    fun addString(chave: String, valor: String) = apply {
        stringMap[chave] = valor
    }

    fun getInt(chave: String): Int = intMap[chave]!!

    fun getFloat(chave: String): Float = floatMap[chave]!!

    fun getString(chave: String): String = stringMap[chave]!!


}