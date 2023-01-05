package gmarques.remotewincontrol.presenter.mouse.mousepad.eventos

import android.view.MotionEvent
import gmarques.remotewincontrol.domain.GestureType
import gmarques.remotewincontrol.presenter.mouse.mousepad.GestoCallback
import kotlin.math.abs

class EventMove(override var callback: GestoCallback) : Event() {

    private var actionDown: Pair<Float, Float>? = null
    private var ignoreLimits = false

    override fun actionDown(event: MotionEvent) {
        if (actionDown == null) actionDown = event.x to event.y
    }

    override fun actionMove(event: MotionEvent) {
        if (actionDown != null && event.pointerCount == 1) {

            val movX = event.x - actionDown!!.first
            val movY = event.y - actionDown!!.second

            if (abs(movX) > MAX_MOV_APR && abs(movY) > MAX_MOV_APR) ignoreLimits = true

            if (ignoreLimits) callback.gestoValidado(GestureType.MOVE, arrayListOf("movX" to movX, "movY" to movY))
        }
    }


    override fun actionUp(event: MotionEvent) {
        actionDown = null
        ignoreLimits = false
    }
}