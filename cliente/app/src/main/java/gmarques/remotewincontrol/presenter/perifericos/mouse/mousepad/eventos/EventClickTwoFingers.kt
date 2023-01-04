package gmarques.remotewincontrol.presenter.perifericos.mouse.mousepad.eventos

import android.view.MotionEvent
import gmarques.remotewincontrol.domain.GestureType
import gmarques.remotewincontrol.presenter.perifericos.mouse.mousepad.SupportedGesturesCallback
import kotlin.math.abs

class EventClickTwoFingers : Event() {

    private var actionDown: Pair<Float, Float>? = null
    override lateinit var callback: SupportedGesturesCallback

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
                callback.clickTwoFingers(GestureType.CLICK_TWO_FINGERS, arrayListOf("" to 0f))

        }
        actionDown = null
    }
}