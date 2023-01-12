package gmarques.remotewincontrol.domain.mouse.scroll

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import gmarques.remotewincontrol.domain.mouse.mousepad_gestos.CLICK_INTERVAL
import gmarques.remotewincontrol.domain.mouse.mousepad_gestos.MAX_MOV_PERM
import kotlin.math.abs

/**
 * Usada como touchlistener para  permitir reconhecer um toque no recyclerview de scroll infitnito
 * Assim que um toque é reconhecido o metodo onclick do RV é chamado
 * */
class ScrollClique : View.OnTouchListener {

    private var actionDown: Pair<Float, Float>? = null


    private fun actionDown(event: MotionEvent) {
        if (actionDown == null) {
            actionDown = event.x to event.y

        }
    }

    private fun actionUp(event: MotionEvent, view: View?) {

        if (actionDown != null && event.pointerCount == 1) {

            val movX = abs(actionDown!!.first - event.x)
            val movY = abs(actionDown!!.second - event.y)

            val duracao = event.eventTime - event.downTime

            if (duracao <= CLICK_INTERVAL && movX <= MAX_MOV_PERM && movY <= MAX_MOV_PERM) {
                view?.performClick()
            }
        }

        actionDown = null

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> actionDown(event)
            MotionEvent.ACTION_UP -> actionUp(event, v)
        }
        return false
    }


}