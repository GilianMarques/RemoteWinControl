package gmarques.remotewincontrol.presenter

import android.content.Context
import android.os.*
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import gmarques.remotewincontrol.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("unused", "unused")
object Vibrador {


    private var vibrandoScroll = false

    private var vib: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
                App.get.applicationContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator

    } else @Suppress("DEPRECATION") App.get.applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    fun vibErro() =
            vib.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))

    fun vibInteracao() =
            vib.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE))

    fun vibScroll(duracao: Int, mCoroutineScope: CoroutineScope) {

        mCoroutineScope.launch {
            if (!vibrandoScroll) {

                vibrandoScroll = true
                vib.vibrate(VibrationEffect.createOneShot(duracao.toLong(), VibrationEffect.EFFECT_TICK))
                delay(duracao.toLong() * 2)
                vibrandoScroll = false
            }
        }

    }

    fun vibSucesso() {
        val vibra = 30L
        val espera = 30L
        vib.vibrate(VibrationEffect.createWaveform(longArrayOf(vibra, espera, vibra, espera, vibra, espera),
            VibrationEffect.DEFAULT_AMPLITUDE))
    }
}