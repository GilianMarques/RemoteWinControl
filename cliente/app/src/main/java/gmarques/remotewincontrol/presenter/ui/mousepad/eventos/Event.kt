package gmarques.remotewincontrol.presenter.ui.mousepad.eventos

import android.view.MotionEvent
import gmarques.remotewincontrol.domain.GestureType
import gmarques.remotewincontrol.presenter.ui.mousepad.SupportedGesturesCallback
import kotlin.collections.ArrayList



/**
 * Abstraçao das classes que analisam entradas do usuario e geram eventos que
 * descrevem essas entradas
 */
abstract class Event {

    /** basta implementar como lateinit na subclasse, será inicializado automaticamente */
    abstract var callback: SupportedGesturesCallback

    open fun actionDown(event: MotionEvent) {}

    open fun actionMove(event: MotionEvent) {}

    open fun actionUp(event: MotionEvent) {}

    open fun actionPointerDown(event: MotionEvent) {}

    open fun actionPointerUp(event: MotionEvent) {}


}