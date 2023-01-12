package gmarques.remotewincontrol

import android.app.Application
import gmarques.remotewincontrol.domain.volume.VolumeHelper
import gmarques.remotewincontrol.rede.io.EnderecosDeRede
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