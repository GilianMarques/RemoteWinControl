package dev.gmarques.remotewincontrol.domain.dtos.servidor

import dev.gmarques.remotewincontrol.domain.JsonMapper

@Suppress("unused")
/**
 * Estrutura de dados usada para enviar os comandos do servidor (Windows) para o cliente (Android)
 */
data class DtoServidor(val tipo: TIPO_EVENTO_SERVIDOR = TIPO_EVENTO_SERVIDOR.NONE) {


    private val intMap = HashMap<String, Int>()
    private val floatMap = HashMap<String, Float>()
    private val stringMap = HashMap<String, String>()


    companion object {
        fun fromJson(json: String) {
            JsonMapper.fromJson(json, DtoServidor::class.java)
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