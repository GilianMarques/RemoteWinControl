package gmarques.remotewincontrol.presenter.ui.mousepad.gestos

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent

import kotlin.math.abs

class GestureLongClick : MousepadGesture() {

    private var actionDown: Pair<Float, Float>? = null

    private var longClick = false
    private var cancelarVerificacao = false

    override fun actionDown(event: MotionEvent) {
        if (actionDown == null) {
            actionDown = event.x to event.y

            Handler(Looper.getMainLooper()).postDelayed({
                if (cancelarVerificacao) cancelarVerificacao = false
                else longClick = true
            }, LONG_CLICK_INTERVAL.toLong())
        }
    }

    override fun actionUp(event: MotionEvent) {

        if (longClick && actionDown != null) {
            val movX = abs(actionDown!!.first - event.x)
            val movY = abs(actionDown!!.second - event.y)

            val duracao = event.eventTime - event.downTime

            if (movX <= MAX_MOV_APR && movY <= MAX_MOV_APR) Log.d("USUK", "LongClickEvent.actionUp: longClick duracao: $duracao movX $movX movY $movY")

        } else cancelarVerificacao = true

        actionDown = null
        longClick = false
    }

}