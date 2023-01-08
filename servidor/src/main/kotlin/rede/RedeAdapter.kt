package rede

import ComandosUsuario.*
import com.google.gson.GsonBuilder
import comandos_remotos.Mouse
import comandos_remotos.Volume
import comandos_remotos.acoes.Acoes
import rede.dtos.ComandoDto
import rede.io.Servidor

/**
 * Converte os comandos recebidos via socket para eventos que podem ser executados localmente
 * @see Servidor
 * */
object RedeAdapter{

    private val gson = GsonBuilder()/*.setPrettyPrinting()*/.create()

    fun anexarListenerAoServidor() {
        Servidor.addListener(::eventoRecebido)
    }

    private fun eventoRecebido(entrada: String) {

        val comando = gson.fromJson(entrada, ComandoDto::class.java)
        println("Comando recebido: $entrada")
        when (comando.tipo) {
            MOUSE_CLICK_ESQ -> Mouse.cliqueEsq()
            MOUSE_CLICK_DIR -> Mouse.cliqueDir()
            MOUSE_CLICK_CEN -> Mouse.cliqueCen()
            PAD_CLICK_TWO_FINGERS -> {}
            PAD_LONG_CLICK -> Mouse.cliqueDir()
            VOLUME_MAIS -> Volume.aumentar()
            VOLUME_MENOS -> Volume.diminuir()
            PAD_MOVE -> Mouse.mover(comando.metadata)
            SCROLL -> Mouse.rolar(comando.metadata)
            ACAO_GRAVAR -> Acoes.gravar()
            ACAO_PARAR_GRAVACAO -> Acoes.pararGravacao(comando)
            else -> {}
        }

    }
}