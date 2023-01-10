package gmarques.remotewincontrol.presenter.volume

import gmarques.remotewincontrol.rede.dtos.cliente.TIPO_DTO_CLIENTE
import gmarques.remotewincontrol.rede.io.RedeAdapter
import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteSemMetadata
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

object VolumeHandler {

    private val INTERVALO = 50L

    private val loopScope = CoroutineScope(IO)
    private val redeAdapter = RedeAdapter

    private lateinit var upJob: Job
    private lateinit var downJob: Job
    private val jobNaoCancelavel = Job()


    fun pressionarVolumeDown() = loopScope.launch(Job().also { downJob = it }) {
        loopScope.launch(jobNaoCancelavel) {
            redeAdapter.enviar(DtoClienteSemMetadata(TIPO_DTO_CLIENTE.VOLUME_MENOS))
            VolumeHelper.setarVolumeOriginal()
        }
        while (true) {
            redeAdapter.enviar(DtoClienteSemMetadata(TIPO_DTO_CLIENTE.VOLUME_MENOS))
            VolumeHelper.setarVolumeOriginal()
            delay(INTERVALO)
        }

    }

    fun pressionarVolumeUp() = loopScope.launch(Job().also { upJob = it }) {

        loopScope.launch(jobNaoCancelavel) {
            redeAdapter.enviar(DtoClienteSemMetadata(TIPO_DTO_CLIENTE.VOLUME_MAIS))
            VolumeHelper.setarVolumeOriginal()
        }

        while (true) {
            redeAdapter.enviar(DtoClienteSemMetadata(TIPO_DTO_CLIENTE.VOLUME_MAIS))
            VolumeHelper.setarVolumeOriginal()
            delay(INTERVALO)
        }

    }

    fun soltouVolumeDown() = downJob.cancel()

    fun soltouVolumeUp() = upJob.cancel()

}