package gmarques.remotewincontrol.presenter.ui

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gmarques.remotewincontrol.App
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.data.PREFS_IP
import gmarques.remotewincontrol.data.PREFS_PORTA
import gmarques.remotewincontrol.data.Preferencias
import gmarques.remotewincontrol.presenter.ComandosUsuario
import gmarques.remotewincontrol.presenter.Vibrador
import gmarques.remotewincontrol.presenter.mouse.EntradaCallback
import gmarques.remotewincontrol.presenter.mouse.MousePadTouchListener
import gmarques.remotewincontrol.presenter.mouse.mousepad_gestos.EntradaClique
import gmarques.remotewincontrol.presenter.mouse.mousepad_gestos.EntradaCliqueDoisDedos
import gmarques.remotewincontrol.presenter.mouse.mousepad_gestos.EntradaCliqueLongo
import gmarques.remotewincontrol.presenter.mouse.mousepad_gestos.EntradaMover
import gmarques.remotewincontrol.presenter.mouse.scroll.ScrollHandler
import gmarques.remotewincontrol.rede.dtos.ComandoDto
import gmarques.remotewincontrol.rede.RedeAdapter
import gmarques.remotewincontrol.rede.EnderecosDeRede
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainViewModel : ViewModel() {

    private lateinit var redeAdapter: RedeAdapter

    private val _vibrarScroll = MutableLiveData(0)
    val vibrarScroll get() = _vibrarScroll

    private val _vibrarMousePad = MutableLiveData(ComandosUsuario.NONE)
    val vibrarMousePad get() = _vibrarMousePad

    init {
        viewModelScope.launch(IO) { redeAdapter = RedeAdapter() }
    }

    fun infiniteScrollListener(direcao: Int) {

        viewModelScope.launch {

            val vibDuracao = (abs(direcao) * 10).coerceIn(10, 100)
            _vibrarScroll.postValue(vibDuracao)

            val sucesso = redeAdapter.enviarGesto(
                ComandoDto(ComandosUsuario.SCROLL,
                    ScrollHandler.getMetadata(direcao)))

            if (!sucesso) {
                val msg = String.format(
                    App.get.getString(R.string.Gesto_do_tipo_x_nao_foi_enviado,
                        ComandosUsuario.SCROLL))
                notificarErroToasty(msg)
            }
        }
    }

    fun getMousePadListener(): MousePadTouchListener {

        val mousePadCallback = EntradaCallback { comandoDto ->
            _vibrarMousePad.value = comandoDto.tipo
            viewModelScope.launch {
                val sucesso = redeAdapter.enviarGesto(comandoDto)
                if (!sucesso) notificarErroToasty(String.format(App.get.getString(R.string.Gesto_do_tipo_x_nao_foi_enviado), comandoDto.tipo))
            }
        }

        //classes que vao identificar entradas do usuario e gerar gestos suportados
        val eventosSuportados = arrayOf(
            EntradaClique(mousePadCallback),
            EntradaCliqueDoisDedos(mousePadCallback),
            EntradaCliqueLongo(mousePadCallback),
            EntradaMover(mousePadCallback))

        return MousePadTouchListener(eventosSuportados)
    }

    fun mouseClique(botao: ComandosUsuario) = viewModelScope.launch {
        _vibrarMousePad.value = botao
        val sucesso = redeAdapter.enviarGesto(ComandoDto(botao))
        if (!sucesso) notificarErroToasty(String.format(App.get.getString(R.string.Gesto_do_tipo_x_nao_foi_enviado), botao))

    }

    private fun notificarErroToasty(mensagem: String) {
        Toast.makeText(App.get, String.format(App.get.getString(R.string.Erro_x), mensagem), Toast.LENGTH_LONG)
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