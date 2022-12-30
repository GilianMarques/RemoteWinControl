package gmarques.remotewincontrol.presenter.ui.mousepad.gestos

import android.view.MotionEvent
import kotlin.reflect.KFunction0

const val CLICK_INTERVAL = 150
const val LONG_CLICK_INTERVAL = 151

/** movimento Maximo Apropriado pra detecçao de cliques e pressionadas*/
const val MAX_MOV_APR = 30

abstract class MousepadGesture {

    var callback: KFunction0<Unit>? = null

    abstract fun actionDown(event: MotionEvent)

    fun actionMove(event: MotionEvent) {}

    abstract fun actionUp(event: MotionEvent)


}