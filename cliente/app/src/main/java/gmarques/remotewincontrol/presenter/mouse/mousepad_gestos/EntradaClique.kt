package gmarques.remotewincontrol.presenter.mouse.mousepad_gestos

import android.view.MotionEvent
import gmarques.remotewincontrol.presenter.mouse.EntradaCallback
import gmarques.remotewincontrol.rede.dtos.cliente.TIPO_DTO_CLIENTE
import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteSemMetadata
import kotlin.math.abs

class EntradaClique(override var callback: EntradaCallback) : EntradaAbs() {

    private var actionDown: Pair<Float, Float>? = null


    override fun actionDown(event: MotionEvent) {
        if (actionDown == null) {
            actionDown = event.x to event.y

        }
    }

    override fun actionUp(event: MotionEvent) {

        if (actionDown != null && event.pointerCount == 1) {

            val movX = abs(actionDown!!.first - event.x)
            val movY = abs(actionDown!!.second - event.y)

            val duracao = event.eventTime - event.downTime

            if (duracao <= CLICK_INTERVAL && movX <= MAX_MOV_PERM && movY <= MAX_MOV_PERM)
                callback.entradaValidada(DtoClienteSemMetadata(TIPO_DTO_CLIENTE.MOUSE_CLICK_ESQ))

        }

        actionDown = null

    }
}