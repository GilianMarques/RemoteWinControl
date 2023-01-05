package gmarques.remotewincontrol.presenter.mouse.mousepad.eventos

import android.view.MotionEvent
import gmarques.remotewincontrol.presenter.mouse.mousepad.GestoCallback


/**
 * Abstraçao das classes que analisam entradas do usuario e geram eventos que
 * descrevem essas entradas
 */
abstract class Event {

    /** callback que ira notificar o cliente quando o gesto for validado*/
    abstract var callback: GestoCallback

    open fun actionDown(event: MotionEvent) {}

    open fun actionMove(event: MotionEvent) {}

    open fun actionUp(event: MotionEvent) {}

    open fun actionPointerDown(event: MotionEvent) {}

    open fun actionPointerUp(event: MotionEvent) {}


}