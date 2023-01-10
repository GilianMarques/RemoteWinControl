package gmarques.remotewincontrol

import android.app.Application
import android.content.Intent
import gmarques.remotewincontrol.presenter.volume.VolumeHelper
import gmarques.remotewincontrol.presenter.volume.ServicoBotoesVolume
import gmarques.remotewincontrol.rede.EnderecosDeRede
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class App : Application() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        get = this
        VolumeHelper.inicializar(this).salvarVolumeAtual()
        GlobalScope.launch { EnderecosDeRede.atualizarEnderecos() }
    }

    companion object {
        lateinit var get: App
    }
}