package gmarques.remotewincontrol.presenter.ui.mousepad

import android.view.MotionEvent
import android.view.View
import gmarques.remotewincontrol.presenter.ui.mousepad.gestos.MousepadGesture

class MousepadHandler(private val gestos: Array<MousepadGesture>) : View.OnTouchListener {

    init {
        gestos.forEach { it.callback = ::gestoIdentificado }
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> gestos.forEach { it.actionDown(event) }
            MotionEvent.ACTION_UP -> gestos.forEach { it.actionUp(event) }
            MotionEvent.ACTION_MOVE -> gestos.forEach { it.actionMove(event) }
        }
        return true
    }


    // TODO: receber aqui os eventos ocorridos, enfileirar e avaliar pra descobrir gestos
    // e passar pro controlador
    private fun gestoIdentificado() {

    }
    private fun avaliarEventos() {

    }

}