package gmarques.remotewincontrol.presenter.mouse.mousepad

import gmarques.remotewincontrol.domain.GestureType

interface GestoCallback {

    fun gestoValidado(tipo: GestureType, metadata: ArrayList<Pair<String, Float>>)

}