package gmarques.remotewincontrol.data

import android.content.Context
import android.content.SharedPreferences
import gmarques.remotewincontrol.App


const val PORTA = "porta"
const val IP = "ip"

class Preferencias {

    private var preferencias: SharedPreferences =
        App.get.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getInt(chave: String, padrao: Int = -1) = preferencias.getInt(chave, padrao)

    fun getString(chave: String, padrao: String?) = preferencias.getString(chave, padrao)


}