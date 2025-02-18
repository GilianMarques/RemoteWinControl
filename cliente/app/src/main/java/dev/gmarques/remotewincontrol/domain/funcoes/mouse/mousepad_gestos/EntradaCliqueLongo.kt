package dev.gmarques.remotewincontrol.domain.funcoes.mouse.mousepad_gestos

import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import dev.gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import dev.gmarques.remotewincontrol.domain.funcoes.mouse.EntradaCallback
import dev.gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente

import kotlin.math.abs

class EntradaCliqueLongo(override var callback: EntradaCallback) : EntradaAbs() {

    private var actionDown: Pair<Float, Float>? = null

    private var longClick = false
    private var cancelarVerificacao = false


    override fun actionDown(event: MotionEvent) {
        if (actionDown == null) {
            actionDown = event.x to event.y

            Handler(Looper.getMainLooper()).postDelayed({
                if (cancelarVerificacao) cancelarVerificacao = false
                else longClick = true
            }, LONG_CLICK_DELAY.toLong())
        }
    }

    override fun actionUp(event: MotionEvent) {

        if (longClick && actionDown != null && event.pointerCount == 1) {

            val movX = abs(actionDown!!.first - event.x)
            val movY = abs(actionDown!!.second - event.y)

            if (movX <= MAX_MOV_PERM && movY <= MAX_MOV_PERM) callback.entradaValidada(DtoCliente(TIPO_EVENTO_CLIENTE.PAD_LONG_CLICK))

        } else cancelarVerificacao = true

        actionDown = null
        longClick = false
    }

}