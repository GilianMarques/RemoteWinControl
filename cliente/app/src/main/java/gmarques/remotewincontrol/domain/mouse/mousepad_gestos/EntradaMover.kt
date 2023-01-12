package gmarques.remotewincontrol.domain.mouse.mousepad_gestos

import android.view.MotionEvent
import gmarques.remotewincontrol.domain.mouse.EntradaCallback
import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteMouseMover
import kotlin.math.abs

class EntradaMover(override var callback: EntradaCallback) : EntradaAbs() {

    private var actionDown: Pair<Float, Float>? = null

    override fun actionDown(event: MotionEvent) {
        if (actionDown == null) actionDown = event.x to event.y
    }

    override fun actionMove(event: MotionEvent) {

        if (event.pointerCount > 1) return

        val movX = event.x - actionDown!!.first
        val movY = event.y - actionDown!!.second
        actionDown = event.x to event.y

        val multiplicador = abs(movX).coerceAtLeast(abs(movY))
        val sense = (multiplicador / 20).coerceIn(1f, 4f)

        val movimentoValido = abs(movX) >= MIN_MOV_PERM || abs(movY) >= MIN_MOV_PERM

        if (movimentoValido) {
            callback.entradaValidada(DtoClienteMouseMover(movX * sense, movY * sense))

        }
    }


    override fun actionUp(event: MotionEvent) {
        actionDown = null
    }
}