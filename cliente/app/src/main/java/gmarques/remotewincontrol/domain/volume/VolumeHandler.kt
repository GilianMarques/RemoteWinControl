package gmarques.remotewincontrol.domain.volume

import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import gmarques.remotewincontrol.rede.io.RedeController
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

object VolumeHandler {

    private val INTERVALO = 50L

    private val loopScope = CoroutineScope(IO)
    private val redeAdapter = RedeController

    private lateinit var upJob: Job
    private lateinit var downJob: Job
    private val jobNaoCancelavel = Job()


    fun pressionarVolumeDown() = loopScope.launch(Job().also { downJob = it }) {
        loopScope.launch(jobNaoCancelavel) {
            redeAdapter.enviar(DtoCliente(TIPO_EVENTO_CLIENTE.VOLUME_MENOS))
            VolumeHelper.setarVolumeOriginal()
        }
        while (true) {
            redeAdapter.enviar(DtoCliente(TIPO_EVENTO_CLIENTE.VOLUME_MENOS))
            VolumeHelper.setarVolumeOriginal()
            delay(INTERVALO)
        }

    }

    fun pressionarVolumeUp() = loopScope.launch(Job().also { upJob = it }) {

        loopScope.launch(jobNaoCancelavel) {
            redeAdapter.enviar(DtoCliente(TIPO_EVENTO_CLIENTE.VOLUME_MAIS))
            VolumeHelper.setarVolumeOriginal()
        }

        while (true) {
            redeAdapter.enviar(DtoCliente(TIPO_EVENTO_CLIENTE.VOLUME_MAIS))
            VolumeHelper.setarVolumeOriginal()
            delay(INTERVALO)
        }

    }

    fun soltouVolumeDown() = downJob.cancel()

    fun soltouVolumeUp() = upJob.cancel()

}