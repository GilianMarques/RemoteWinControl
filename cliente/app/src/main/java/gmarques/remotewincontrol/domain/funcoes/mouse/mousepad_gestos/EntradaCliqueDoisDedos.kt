package gmarques.remotewincontrol.domain.funcoes.mouse.mousepad_gestos

import android.view.MotionEvent
import gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import gmarques.remotewincontrol.domain.funcoes.mouse.EntradaCallback
import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import kotlin.math.abs

class EntradaCliqueDoisDedos(override var callback: EntradaCallback) : EntradaAbs() {

    private var actionDown: Pair<Float, Float>? = null

    override fun actionDown(event: MotionEvent) {
        if (actionDown == null) {
            actionDown = event.x to event.y

        }
        super.actionDown(event)
    }


    override fun actionPointerUp(event: MotionEvent) {

        if (actionDown != null && event.pointerCount == 2) {

            val movX = abs(actionDown!!.first - event.x)
            val movY = abs(actionDown!!.second - event.y)

            val duracao = event.eventTime - event.downTime

            if (duracao <= CLICK_INTERVAL_TWO_FINGERS && movX <= MAX_MOV_PERM_TWO_FINGERS && movY <= MAX_MOV_PERM_TWO_FINGERS)
                callback.entradaValidada(DtoCliente(TIPO_EVENTO_CLIENTE.PAD_CLICK_TWO_FINGERS))

        }
        actionDown = null
    }
}