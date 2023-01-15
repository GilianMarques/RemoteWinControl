package gmarques.remotewincontrol.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import gmarques.remotewincontrol.App


class Preferencias {

    private var preferencias: SharedPreferences = App.get
        .getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getInt(chave: String, padrao: Int = -1) = preferencias.getInt(chave, padrao)

    fun getFloat(chave: String, padrao: Float = -1f) = preferencias.getFloat(chave, padrao)

    fun getString(chave: String, padrao: String = "") = preferencias.getString(chave, padrao)


    fun putInt(chave: String, valor: Int) = preferencias.edit { putInt(chave, valor) }

    fun putFloat(chave: String, valor: Float) = preferencias.edit { putFloat(chave, valor) }

    fun putString(chave: String, valor: String) = preferencias.edit { putString(chave, valor) }

    /**
     * Remove a chave do arquivo xml de forma que e como se uma valor daquela chave nunca
     * tivesse sido salvo no arquivo
     */
    fun remover(chave: String) = preferencias.edit { remove(chave) }


}