package gmarques.remotewincontrol.domain.mouse

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import gmarques.remotewincontrol.domain.mouse.mousepad_gestos.EntradaAbs

/**
 * Recebe as entradas do usuario direto da view e passa para serem analisados
 * pelas classes de eventos
 * @See EntradaAbs
 * */
class MousePadTouchListener(
    private val eventos: Array<EntradaAbs>,
) : View.OnTouchListener {


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> eventos.forEach { it.actionDown(event) }
            MotionEvent.ACTION_POINTER_DOWN -> eventos.forEach { it.actionPointerDown(event) }
            MotionEvent.ACTION_UP -> eventos.forEach { it.actionUp(event) }
            MotionEvent.ACTION_POINTER_UP -> eventos.forEach { it.actionPointerUp(event) }
            MotionEvent.ACTION_MOVE -> eventos.forEach { it.actionMove(event) }
        }
        return true
    }






}