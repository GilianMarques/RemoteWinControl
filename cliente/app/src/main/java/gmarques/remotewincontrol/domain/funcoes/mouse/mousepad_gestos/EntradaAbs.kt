package gmarques.remotewincontrol.domain.funcoes.mouse.mousepad_gestos

import android.view.MotionEvent
import gmarques.remotewincontrol.domain.funcoes.mouse.EntradaCallback


/**
 * Abstraçao das classes que analisam entradas do usuario e geram eventos que
 * descrevem essas entradas
 */
abstract class EntradaAbs {

    /** callback que ira notificar o cliente quando o gesto for validado*/
    abstract var callback: EntradaCallback

    open fun actionDown(event: MotionEvent) {}

    open fun actionMove(event: MotionEvent) {}

    open fun actionUp(event: MotionEvent) {}

    open fun actionPointerDown(event: MotionEvent) {}

    open fun actionPointerUp(event: MotionEvent) {}


}