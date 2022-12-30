package gmarques.remotewincontrol

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        get = this
    }

    companion object {
        lateinit var get: App
    }
}