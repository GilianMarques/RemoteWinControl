package rede.io

import domain.acoes.Acao
import rede.dtos.cliente.TIPO_DTO_CLIENTE.*
import domain.reprodutores.Mouse
import domain.reprodutores.Volume
import domain.acoes.AcaoController
import globalScope
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import rede.JsonMapper
import rede.dtos.cliente.*
import rede.dtos.servidor.DtoServidorAbs

/**
 * Converte os comandos recebidos via “socket” para eventos que podem ser executados localmente
 * @see Servidor
 * */
object RedeController {

    private val servidor = Servidor()
    private val cliente = Cliente()

    fun iniciarServidorAsync() = CoroutineScope(Job() + IO).launch {
        servidor.ligar(::eventoRecebido)
    }

    private fun eventoRecebido(entrada: String) {

        // Aqui, desserializo o json usando uma das subclasses Dto apenas para acessar o tipo
        // Com essa info posso desserializar usando a classe correta posteriormente
        val comando = JsonMapper.fromJson(entrada, DtoClienteMouseMover::class.java)

        println("Comando recebido: $entrada")
// TODO: deve ter apenas um objeto dto para o cliente e outro para o servidor, esses devem transportar as
// TODO: classes com as açoes e metadados. Classses dto devem ficar visiveis apenas no pacote rede
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
            ACAO_GRAVAR -> AcaoController.gravar()
            ACAO_PARAR_GRAVACAO -> AcaoController.pararGravacao(
                JsonMapper.fromJson(
                    entrada,
                    DtoClienteSemMetadata::class.java
                )
            )

            ACAO_ABORTAR_GRAVACAO -> AcaoController.abortarGravacao()

            ACAO_REPRODUZIR_GRAVACAO -> reproduzirAcao(entrada)
        }

    }

    private fun reproduzirAcao(entrada: String) {

        val dto = JsonMapper.fromJson(entrada, DtoClienteReproduzirAcao::class.java)
        val acao = JsonMapper.fromJson(dto.acao, Acao::class.java)

        AcaoController.reproduzir(acao)
    }

    suspend fun enviar(ip: String, porta: Int, data: DtoServidorAbs) = withContext(IO) {
        cliente.enviarMsg(data.toJson(), porta, ip)
    }
}