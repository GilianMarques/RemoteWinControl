package gmarques.remotewincontrol.presenter.volume

import gmarques.remotewincontrol.presenter.ComandosUsuario
import gmarques.remotewincontrol.rede.RedeAdapter
import gmarques.remotewincontrol.rede.dtos.ComandoDto
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class VolumeHandler {

    private val INTERVALO = 50L

    private val scope = CoroutineScope(IO)
    private val redeAdapter = RedeAdapter()

    private lateinit var upJob: Job
    private lateinit var downJob: Job


    fun pressionouVolumeDown() = scope.launch(Job().also { downJob = it }) {
        while (true) {
            redeAdapter.manipularVolume(ComandoDto(ComandosUsuario.VOLUME_MENOS))
            delay(INTERVALO)
        }

    }

    fun pressionouVolumeUp() = scope.launch(Job().also { upJob = it }) {
        while (true) {
            redeAdapter.manipularVolume(ComandoDto(ComandosUsuario.VOLUME_MAIS))
            delay(INTERVALO)
        }

    }

    fun soltouVolumeDown() = downJob.cancel()

    fun soltouVolumeUp() = upJob.cancel()

}