package gmarques.remotewincontrol.presenter.ui

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gmarques.remotewincontrol.App
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.data.PREFS_IP
import gmarques.remotewincontrol.data.PREFS_PORTA
import gmarques.remotewincontrol.data.Preferencias
import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import gmarques.remotewincontrol.domain.mouse.EntradaCallback
import gmarques.remotewincontrol.domain.mouse.MousePadTouchListener
import gmarques.remotewincontrol.domain.mouse.mousepad_gestos.EntradaClique
import gmarques.remotewincontrol.domain.mouse.mousepad_gestos.EntradaCliqueDoisDedos
import gmarques.remotewincontrol.domain.mouse.mousepad_gestos.EntradaCliqueLongo
import gmarques.remotewincontrol.domain.mouse.mousepad_gestos.EntradaMover
import gmarques.remotewincontrol.domain.mouse.scroll.ScrollHandler
import gmarques.remotewincontrol.presenter.Vibrador
import gmarques.remotewincontrol.rede.io.EnderecosDeRede
import gmarques.remotewincontrol.rede.io.RedeController
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlin.math.abs


class MainViewModel : ViewModel() {


    private val _vibrarScroll = MutableLiveData(0)
    val vibrarScroll: LiveData<Int> get() = _vibrarScroll

    private val _vibrarMousePad = MutableLiveData(TIPO_EVENTO_CLIENTE.NONE)
    val vibrarMousePad: LiveData<TIPO_EVENTO_CLIENTE> get() = _vibrarMousePad


    fun infiniteScrollListener(direcao: Int) {

        viewModelScope.launch {

            val vibDuracao = (abs(direcao) * 10).coerceIn(10, 100)
            _vibrarScroll.postValue(vibDuracao)

            val sucesso = ScrollHandler.enviarEvento(direcao)
            if (!sucesso) {
                val msg = String.format(
                    App.get.getString(
                        R.string.Gesto_do_tipo_x_nao_foi_enviado,
                        TIPO_EVENTO_CLIENTE.MOUSE_SCROLL
                    )
                )
                notificarErroToasty(msg)
            }

        }
    }

    fun getMousePadListener(): MousePadTouchListener {

        val mousePadCallback = EntradaCallback { comandoDto ->
            _vibrarMousePad.value = comandoDto.tipo
            viewModelScope.launch {
                val sucesso = RedeController.enviar(comandoDto)
                if (!sucesso) notificarErroToasty(
                    String.format(
                        App.get.getString(R.string.Gesto_do_tipo_x_nao_foi_enviado),
                        comandoDto.tipo
                    )
                )
            }
        }

        //classes que vao identificar entradas do usuario e gerar gestos suportados
        val eventosSuportados = arrayOf(
            EntradaClique(mousePadCallback),
            EntradaCliqueDoisDedos(mousePadCallback),
            EntradaCliqueLongo(mousePadCallback),
            EntradaMover(mousePadCallback)
        )

        return MousePadTouchListener(eventosSuportados)
    }

    fun mouseClique(botao: TIPO_EVENTO_CLIENTE) = viewModelScope.launch {
        _vibrarMousePad.value = botao
        val sucesso = RedeController.enviar(DtoCliente(botao))
        if (!sucesso) notificarErroToasty(
            String.format(
                App.get.getString(R.string.Gesto_do_tipo_x_nao_foi_enviado),
                botao
            )
        )

    }

    private fun notificarErroToasty(mensagem: String) {
        Toast.makeText(
            App.get,
            String.format(App.get.getString(R.string.Erro_x), mensagem),
            Toast.LENGTH_LONG
        )
            .show()
        Vibrador.vibErro()
    }

    fun atualizarEnderecosEnotificar(porta: Int?, ip: String) = viewModelScope.launch(IO) {
        val prefs = Preferencias()

        if (ip.isEmpty()) prefs.remover(PREFS_IP)
        else prefs.putString(PREFS_IP, ip)

        if (porta == null) prefs.remover(PREFS_PORTA)
        else prefs.putInt(PREFS_PORTA, porta)

        EnderecosDeRede.atualizarEnderecos()
    }

}