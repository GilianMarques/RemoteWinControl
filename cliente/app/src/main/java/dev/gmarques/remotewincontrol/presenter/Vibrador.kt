package dev.gmarques.remotewincontrol.presenter

import android.content.Context
import android.os.*
import dev.gmarques.remotewincontrol.App
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

    fun vibSucesso() {
        val vibra = 30L
        val espera = 30L
        vib.vibrate(VibrationEffect.createWaveform(longArrayOf(espera, vibra, espera, vibra, espera, vibra),
            VibrationEffect.DEFAULT_AMPLITUDE))
    }

    fun vibDesligar() {
        val vibra = 500L
        val espera = 30L
        vib.vibrate(VibrationEffect.createWaveform(longArrayOf(espera, vibra, espera, vibra, espera, vibra, espera, vibra, espera, vibra, espera / 2, vibra / 2, espera / 2, vibra / 2, espera / 2, vibra / 2, espera / 3, vibra / 3, espera / 3, vibra / 3, espera / 3, vibra * 3),
            VibrationEffect.DEFAULT_AMPLITUDE))
    }

    fun vibScroll(duracao: Int, mCoroutineScope: CoroutineScope) {

        mCoroutineScope.launch {
            if (!vibrandoScroll) {

                vibrandoScroll = true
                vib.vibrate(VibrationEffect.createOneShot(duracao.toLong(), VibrationEffect.EFFECT_TICK))
                delay(duracao.toLong() / 3)
                vibrandoScroll = false
            }
        }

    }

    fun vibLongCLick() =
            vib.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))

    fun vibCLick() =
            vib.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))

    fun vibClickTwoFingers() {
        val espera = 75L
        val vibra = 50L
        vib.vibrate(VibrationEffect.createWaveform(longArrayOf(/*espera*/0, vibra, espera, vibra),
            VibrationEffect.DEFAULT_AMPLITUDE))
    }

}
