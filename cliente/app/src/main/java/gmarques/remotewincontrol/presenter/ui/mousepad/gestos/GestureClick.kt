package gmarques.remotewincontrol.presenter.ui.mousepad.gestos

import android.util.Log
import android.view.MotionEvent
import kotlin.math.abs

class GestureClick : MousepadGesture() {

    private var actionDown: Pair<Float, Float>? = null

    override fun actionDown(event: MotionEvent) {
        if (actionDown == null) {
            actionDown = event.x to event.y

        }
    }

    override fun actionUp(event: MotionEvent) {
        if (actionDown != null) {

            val movX = abs(actionDown!!.first - event.x)
            val movY = abs(actionDown!!.second - event.y)

            val duracao = event.eventTime - event.downTime

            if (duracao <= CLICK_INTERVAL && movX <= MAX_MOV_APR && movY <= MAX_MOV_APR) Log.d("USUK", "ClickEvent.actionUp: click duracao: $duracao movX $movX movY $movY")

            actionDown = null
        }
    }
}