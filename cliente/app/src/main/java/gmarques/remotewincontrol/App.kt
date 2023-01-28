package gmarques.remotewincontrol

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import gmarques.remotewincontrol.data.PREFS_TIPO_NIGHT_MODE
import gmarques.remotewincontrol.data.Preferencias
import gmarques.remotewincontrol.rede.io.EnderecosDeRede
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class App : Application() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()

        get = this

        Preferencias()
            .getInt(PREFS_TIPO_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            .also { AppCompatDelegate.setDefaultNightMode(it) }



        GlobalScope.launch { EnderecosDeRede.atualizarEnderecos() }
    }

    companion object {
        lateinit var get: App
    }
}