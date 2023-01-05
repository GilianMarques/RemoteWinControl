package gmarques.remotewincontrol.presenter.mouse.mousepad.eventos

import android.view.MotionEvent
import gmarques.remotewincontrol.domain.GestureType
import gmarques.remotewincontrol.presenter.mouse.mousepad.GestoCallback
import kotlin.math.abs

class EventClickTwoFingers(override var callback: GestoCallback) : Event() {

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

            if (duracao <= CLICK_INTERVAL_TWO_FINGERS && movX <= MAX_MOV_APR_TWO_FINGERS && movY <= MAX_MOV_APR_TWO_FINGERS)
                callback.gestoValidado(GestureType.CLICK_TWO_FINGERS, arrayListOf("" to 0f))

        }
        actionDown = null
    }
}