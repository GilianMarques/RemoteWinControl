package gmarques.remotewincontrol.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import gmarques.remotewincontrol.App
import gmarques.remotewincontrol.domain.funcoes.acoes.Acao
import gmarques.remotewincontrol.domain.JsonMapper

/**
 * Uma implementação tipo "DAO" para armazenar as açoes gravadas no Computador
 * porem usando a API Preferences ao inves de um banco de dados convencional
 * */
class AcoesDao {

    private var preferencias: SharedPreferences = App.get
        .getSharedPreferences("app_acoes", Context.MODE_PRIVATE)

    fun getAcoes(): ArrayList<Acao> {
        val acoes = ArrayList<Acao>()
        preferencias.all.values.forEach { json ->
            json.let { acoes.add(JsonMapper.fromJson(json as String, Acao::class.java)) }

        }
        acoes.sortBy { it.nome }
        return acoes
    }

    fun getAcao(chave: String): Acao? {
        val json = preferencias.getString(chave, null)
        return if (json != null) JsonMapper.fromJson(json, Acao::class.java)
        else null
    }

    fun salvarAcao(acao: Acao) = preferencias.edit(true) { putString(acao.id, JsonMapper.toJson(acao)) }

    /**
     * Remove a chave do arquivo xml de forma que e como se uma valor daquela chave nunca
     * tivesse sido salvo no arquivo
     */
    fun removerAcao(chave: String) = preferencias.edit { remove(chave) }


}