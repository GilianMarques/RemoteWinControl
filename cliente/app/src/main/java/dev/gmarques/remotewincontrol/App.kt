package dev.gmarques.remotewincontrol

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dev.gmarques.remotewincontrol.data.PREFS_TIPO_NIGHT_MODE
import dev.gmarques.remotewincontrol.data.Preferencias
import kotlinx.coroutines.DelicateCoroutinesApi

class App : Application() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()

        get = this

        Preferencias()
            .getInt(PREFS_TIPO_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            .also { AppCompatDelegate.setDefaultNightMode(it) }

    }

    companion object {
        lateinit var get: App
    }
}