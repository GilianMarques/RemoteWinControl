package rede.io

import rede.dtos.cliente.TIPO_DTO_CLIENTE.*
import com.google.gson.GsonBuilder
import comandos_remotos.Mouse
import comandos_remotos.Volume
import comandos_remotos.acoes.Acoes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rede.dtos.cliente.*
import rede.dtos.servidor.DtoServidorAbs

/**
 * Converte os comandos recebidos via socket para eventos que podem ser executados localmente
 * @see Servidor
 * */
object RedeAdapter {

    private val gson = GsonBuilder().create()
    private val servidor = Servidor()
    private val cliente = Cliente()

    fun iniciarServidor() = CoroutineScope(IO).launch {
        servidor.ligar(::eventoRecebido)
    }

    private fun eventoRecebido(entrada: String) {

        // aqui desserializao o json usando uma das subclasses Dto apenas para acessar o tipo
        //com essa info posso desserializar usando a classe correta posteriormente
        val comando = gson.fromJson(entrada, DtoClienteMouseMover::class.java)

        println("Comando recebido: $entrada")

        when (comando.tipo) {
            MOUSE_CLICK_ESQ -> Mouse.cliqueEsq()
            MOUSE_CLICK_DIR -> Mouse.cliqueDir()
            MOUSE_CLICK_CEN -> Mouse.cliqueCen()
            PAD_CLICK_TWO_FINGERS -> {}
            PAD_LONG_CLICK -> Mouse.cliqueDir()
            VOLUME_MAIS -> Volume.aumentar()
            VOLUME_MENOS -> Volume.diminuir()
            PAD_MOVE -> Mouse.mover(gson.fromJson(entrada, DtoClienteMouseMover::class.java))
            SCROLL -> Mouse.rolar(gson.fromJson(entrada, DtoClienteMouseRolar::class.java))
            ACAO_GRAVAR -> Acoes.gravar()
            ACAO_PARAR_GRAVACAO -> Acoes.pararGravacao(gson.fromJson(entrada, DtoClienteSemMetadata::class.java))
        }

    }

    suspend fun enviar(ip: String, porta: Int, data: DtoServidorAbs) = withContext(IO) {
        cliente.enviarMsg(data.toJson(), porta, ip)
    }
}