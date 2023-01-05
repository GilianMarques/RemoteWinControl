package gmarques.remotewincontrol.presenter.mouse.mousepad.eventos

import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import gmarques.remotewincontrol.domain.GestureType
import gmarques.remotewincontrol.presenter.mouse.mousepad.GestoCallback

import kotlin.math.abs

class EventLongClick(override var callback: GestoCallback) : Event() {

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

            if (movX <= MAX_MOV_APR && movY <= MAX_MOV_APR) callback.gestoValidado(GestureType.LONG_CLICK, arrayListOf("" to 0f))

        } else cancelarVerificacao = true

        actionDown = null
        longClick = false
    }

}