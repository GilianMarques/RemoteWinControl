package gmarques.remotewincontrol.presenter.mouse.mousepad_gestos

import android.util.Log
import android.view.MotionEvent
import gmarques.remotewincontrol.presenter.EntradaUsuario
import gmarques.remotewincontrol.presenter.mouse.EntradaCallback
import gmarques.remotewincontrol.rede.dtos.ComandoDto
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


        Log.d("USUK", "EntradaMover.actionMove (no sense): movX $movX  movY $movY sense $sense")


        val movimentoValido = abs(movX) >= MIN_MOV_PERM || abs(movY) >= MIN_MOV_PERM

        if (movimentoValido) {
            callback.entradaValidada(ComandoDto(
                EntradaUsuario.PAD_MOVE, movX * sense, movY * sense))

        }
    }


    override fun actionUp(event: MotionEvent) {
        actionDown = null
    }
}