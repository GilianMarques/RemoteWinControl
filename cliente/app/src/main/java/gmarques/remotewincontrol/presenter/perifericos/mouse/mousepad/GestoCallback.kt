package gmarques.remotewincontrol.presenter.perifericos.mouse.mousepad

import gmarques.remotewincontrol.domain.GestureType

interface GestoCallback {

    fun gestoValidado(tipo: GestureType, metadata: ArrayList<Pair<String, Float>>)

}