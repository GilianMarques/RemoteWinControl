package rede.io

import rede.dtos.cliente.TIPO_DTO_CLIENTE.*
import comandos_remotos.Mouse
import comandos_remotos.Volume
import comandos_remotos.acoes.Acoes
import globalScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import rede.JsonMapper
import rede.dtos.cliente.*
import rede.dtos.servidor.DtoServidorAbs

/**
 * Converte os comandos recebidos via “socket” para eventos que podem ser executados localmente
 * @see Servidor
 * */
object RedeAdapter {

    private val servidor = Servidor()
    private val cliente = Cliente()

    fun iniciarServidorAsync() = globalScope.async {
        servidor.ligar(::eventoRecebido)
    }

    private fun eventoRecebido(entrada: String) {

        // Aqui, desserializo o json usando uma das subclasses Dto apenas para acessar o tipo
        // Com essa info posso desserializar usando a classe correta posteriormente
        val comando = JsonMapper.fromJson(entrada, DtoClienteMouseMover::class.java)

        println("Comando recebido: $entrada")

        when (comando.tipo) {
            MOUSE_CLICK_ESQ -> Mouse.cliqueEsq()
            MOUSE_CLICK_DIR -> Mouse.cliqueDir()
            MOUSE_CLICK_CEN -> Mouse.cliqueCen()
            PAD_CLICK_TWO_FINGERS -> {}
            PAD_LONG_CLICK -> Mouse.cliqueDir()
            VOLUME_MAIS -> Volume.aumentar()
            VOLUME_MENOS -> Volume.diminuir()
            PAD_MOVE -> Mouse.mover(JsonMapper.fromJson(entrada, DtoClienteMouseMover::class.java))
            SCROLL -> Mouse.rolar(JsonMapper.fromJson(entrada, DtoClienteMouseRolar::class.java))
            ACAO_GRAVAR -> Acoes.gravar()
            ACAO_PARAR_GRAVACAO -> Acoes.pararGravacao(JsonMapper.fromJson(entrada, DtoClienteSemMetadata::class.java))
            ACAO_REPRODUZIR_GRAVACAO -> Acoes.reproduzir(JsonMapper.fromJson(entrada, DtoClienteReproduzirAcao::class.java))
        }

    }

    suspend fun enviar(ip: String, porta: Int, data: DtoServidorAbs) = withContext(IO) {
        cliente.enviarMsg(data.toJson(), porta, ip)
    }
}