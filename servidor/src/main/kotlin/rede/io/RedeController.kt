package rede.io

import domain.Desligamento
import domain.acoes.AcaoController
import domain.dtos.cliente.DtoCliente
import domain.dtos.servidor.DtoServidor
import domain.reprodutores.Mouse
import domain.reprodutores.Volume
import domain.dtos.cliente.TIPO_EVENTO_CLIENTE.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import rede.JsonMapper

/**
 * Converte os comandos recebidos via “socket” para eventos que podem ser executados localmente
 * @see Servidor
 * */
object RedeController {

    private val servidor = Servidor()
    private val cliente = Cliente()
    private val execScope = CoroutineScope(IO)

    fun getPortaDoServidor() = servidor.porta


    fun iniciarServidorAsync() = CoroutineScope(Job() + IO).launch {
        servidor.ligar { execScope.launch { eventoRecebido(it) } }
    }

    private suspend fun eventoRecebido(entrada: String) = withContext(IO) {

        val comando = JsonMapper.fromJson(entrada, DtoCliente::class.java)

        println("Comando recebido: $entrada")

        when (comando.tipo) {
            MOUSE_CLICK_ESQ -> Mouse.cliqueEsq()
            MOUSE_CLICK_DIR -> Mouse.cliqueDir()
            MOUSE_CLICK_CEN -> Mouse.cliqueCen()
            PAD_CLICK_TWO_FINGERS -> {}
            PAD_LONG_CLICK -> Mouse.cliqueDir()
            VOLUME_MAIS -> Volume.aumentar()
            VOLUME_MENOS -> Volume.diminuir()
            MOUSE_MOVE -> Mouse.mover(comando)
            MOUSE_SCROLL -> Mouse.rolar(comando)
            ACAO_GRAVAR -> AcaoController.gravar(comando)
            ACAO_PARAR_GRAVACAO -> AcaoController.pararGravacao(comando)
            ACAO_ABORTAR_GRAVACAO -> AcaoController.abortarGravacao()
            ACAO_REPRODUZIR_GRAVACAO -> AcaoController.reproduzir(comando)
            ACAO_PARAR_REPRODUCAO -> AcaoController.pararReproducao()
            AGENDAR_DESLIGAMENTO -> Desligamento.agendar(comando)
            ABORTAR_DESLIGAMENTO -> Desligamento.abortar()
            NONE -> {}
        }
    }

    suspend fun enviar(ip: String, porta: Int, data: DtoServidor) = withContext(IO) {
        cliente.enviarMsg(data.toJson(), porta, ip)
    }
}