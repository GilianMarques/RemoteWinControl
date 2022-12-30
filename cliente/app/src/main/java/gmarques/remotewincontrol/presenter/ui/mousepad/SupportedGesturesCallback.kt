package gmarques.remotewincontrol.presenter.ui.mousepad

import gmarques.remotewincontrol.domain.GestureType

interface SupportedGesturesCallback {
    fun click(tipo: GestureType, metadata: ArrayList<Pair<String, Float>>)
    fun clickTwoFingers(tipo: GestureType, metadata: ArrayList<Pair<String, Float>>)
    fun move(tipo: GestureType, metadata: ArrayList<Pair<String, Float>>)
    fun longClick(tipo: GestureType, metadata: ArrayList<Pair<String, Float>>)

}