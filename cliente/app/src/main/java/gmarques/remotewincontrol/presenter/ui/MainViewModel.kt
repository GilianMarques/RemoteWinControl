package gmarques.remotewincontrol.presenter.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.abs

class MainViewModel : ViewModel() {

    private val _vibrarScroll = MutableLiveData(1)
    val vibrarScroll get() = _vibrarScroll

    fun infiniteScrollListener(direcao: Int) {

        // garante que a duraçao seja positiva, ja que direçao pode ser negativo
        var duracao = abs(direcao)
        //garante que a duraçao que seja >=10 e <=100 millis
        duracao = 10.coerceAtLeast(duracao * 10).coerceAtMost(100)

        _vibrarScroll.postValue(duracao)
    }


}