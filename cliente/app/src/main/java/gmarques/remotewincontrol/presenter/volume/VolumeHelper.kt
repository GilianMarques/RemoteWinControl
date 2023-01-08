package gmarques.remotewincontrol.presenter.volume

import android.content.Context
import android.media.AudioManager
import androidx.core.content.ContextCompat

/**
 * Captura o estado do volume e o mantem como estava mesmo
 * quando o usuario usa as teclas de volume para controlar o computador
 */
object VolumeHelper {

    private var audio: AudioManager? = null
    private var currentVolume: Int = 0

    fun inicializar(context: Context) = apply {
        audio = ContextCompat.getSystemService(context, AudioManager::class.java)
    }


    fun setarVolumeOriginal() {
        audio?.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)
    }

    fun salvarVolumeAtual() {
        currentVolume = audio!!.getStreamVolume(AudioManager.STREAM_MUSIC)
        // val maxVolume = audio!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    }

}