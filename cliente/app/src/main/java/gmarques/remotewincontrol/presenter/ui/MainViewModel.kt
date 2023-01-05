package gmarques.remotewincontrol.presenter.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gmarques.remotewincontrol.domain.GestureType
import gmarques.remotewincontrol.presenter.mouse.mousepad.MousePadTouchListener
import gmarques.remotewincontrol.presenter.mouse.mousepad.GestoCallback
import gmarques.remotewincontrol.presenter.mouse.mousepad.eventos.EventClick
import gmarques.remotewincontrol.presenter.mouse.mousepad.eventos.EventClickTwoFingers
import gmarques.remotewincontrol.presenter.mouse.mousepad.eventos.EventLongClick
import gmarques.remotewincontrol.presenter.mouse.mousepad.eventos.EventMove
import kotlin.math.abs

class MainViewModel : ViewModel() {

    private val _vibrarScroll = MutableLiveData(0)
    val vibrarScroll get() = _vibrarScroll

    private val _vibrarMousePad = MutableLiveData(GestureType.NONE)
    val vibrarMousePad get() = _vibrarMousePad

    fun infiniteScrollListener(direcao: Int) {

        // garante que a duraçao seja positiva, ja que direçao pode ser negativo
        var duracao = abs(direcao)
        //garante que a duraçao que seja >=10 e <=100 millis
        duracao = 10.coerceAtLeast(duracao * 10).coerceAtMost(100)

        _vibrarScroll.postValue(duracao)
    }

    fun getMousePadHandler(): MousePadTouchListener {

         val mousePadCallback = object : GestoCallback {

            override fun gestoValidado(tipo: GestureType, metadata: ArrayList<Pair<String, Float>>) {
                Log.d("USUK", "MainViewModel.".plus("gesto tipo = '$tipo', metadata = '$metadata'"))
                _vibrarMousePad.value = tipo
            }

        }

        //classes que vao identificar entradas do usuario e gerar eventos
        //que por sua vez serao analizados para identificar gestos suportados
        val eventos = arrayOf(
            EventClick(mousePadCallback),
            EventClickTwoFingers(mousePadCallback),
            EventLongClick(mousePadCallback),
            EventMove(mousePadCallback))

        return MousePadTouchListener(eventos)
    }



    fun mouseClique(botao: Int) {
        when (botao) {
            1 -> {
                _vibrarMousePad.value = GestureType.CLICK
                // TODO: passar pro controlador
            }
            2 -> {
                _vibrarMousePad.value = GestureType.LONG_CLICK
                // TODO: passar pro controlador
            }
            3 -> {
                _vibrarMousePad.value = GestureType.CLICK_TWO_FINGERS
                // TODO: passar pro controlador
            }
        }

    }

}