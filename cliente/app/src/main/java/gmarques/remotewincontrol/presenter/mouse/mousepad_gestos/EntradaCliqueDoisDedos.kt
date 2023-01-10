package gmarques.remotewincontrol.presenter.mouse.mousepad_gestos

import android.view.MotionEvent
import gmarques.remotewincontrol.rede.dtos.cliente.TIPO_DTO_CLIENTE
import gmarques.remotewincontrol.presenter.mouse.EntradaCallback
import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteSemMetadata
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
                callback.entradaValidada(DtoClienteSemMetadata(TIPO_DTO_CLIENTE.PAD_CLICK_TWO_FINGERS))

        }
        actionDown = null
    }
}