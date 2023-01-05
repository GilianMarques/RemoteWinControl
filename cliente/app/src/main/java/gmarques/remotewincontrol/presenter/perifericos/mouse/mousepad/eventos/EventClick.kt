package gmarques.remotewincontrol.presenter.perifericos.mouse.mousepad.eventos

import android.view.MotionEvent
import gmarques.remotewincontrol.domain.GestureType
import gmarques.remotewincontrol.presenter.perifericos.mouse.mousepad.GestoCallback
import kotlin.math.abs

class EventClick(override var callback: GestoCallback) : Event() {

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

            if (duracao <= CLICK_INTERVAL && movX <= MAX_MOV_APR && movY <= MAX_MOV_APR)
                callback.gestoValidado(GestureType.CLICK, arrayListOf("" to 0f))

        }

        actionDown = null

    }
}