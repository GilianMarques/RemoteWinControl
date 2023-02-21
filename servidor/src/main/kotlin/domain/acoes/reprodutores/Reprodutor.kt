package domain.acoes.reprodutores

import domain.acoes.Acao
import domain.acoes.Etapa
import domain.acoes.Etapa.TIPO.*
import domain.dtos.cliente.DtoCliente
import domain.dtos.servidor.DtoServidor
import domain.reprodutores.Mouse
import gmarques.remotewincontrol.domain.dtos.servidor.TIPO_EVENTO_SERVIDOR
import kotlinx.coroutines.*
import rede.io.RedeController

object Reprodutor {

    private var scopeExecucao = CoroutineScope(Job())
    private val reprodutorMouse = ReprodutorMouse()
    private val reprodutorTeclado = ReprodutorTeclado()
    private lateinit var comando: DtoCliente
    private var reproduzindo = false
    private val acoes = ArrayList<Acao>()

    fun reproduzir(acao: Acao, comando: DtoCliente) {
        this@Reprodutor.comando = comando

        acoes.add(acao)

        if (!reproduzindo) iterarSobreAcoes()
        else exibirDescricao("${acao.nome} adiconada a fila na posição #${acoes.size}")

    }

    private fun iterarSobreAcoes(): Any = scopeExecucao.launch {
        reproduzindo = true
        val acao = acoes[0]
        exibirDescricao("iterarSobreAcoes ação: '${acao.nome}' em ${acao.velocidade}x")
        Mouse.salvarPosicaoAtualDoMouse()
        iterarSobreEtapas(acao)
        Mouse.restaurarPosicaoOriginalDoMouse()
        exibirDescricao("ação executada")

        acoes.removeAt(0)

        if (acoes.size > 0) this@Reprodutor.iterarSobreAcoes()
        else {
            reproduzindo = false
            notificarClienteSobreFimDaReproducao()
        }

    }


    private suspend fun iterarSobreEtapas(acao: Acao) {
        for (i in 0 until acao.etapas.size) {
            val etapa = acao.etapas[i]
            executarAcao(etapa)
            delay(calcularDelay(acao, i, etapa))
        }
    }

    private fun executarAcao(etapa: Etapa) {
        when (etapa.tipo) {
            TECLADO_PRESS -> reprodutorTeclado.tecladoPressionarTecla(etapa)
            TECLADO_SOLTAR -> reprodutorTeclado.tecladoSoltarTecla(etapa)
            MOUSE_MOVER -> reprodutorMouse.mouseMover(etapa)
            MOUSE_ROLAR -> reprodutorMouse.mouseRolar(etapa)
            MOUSE_PRESS -> reprodutorMouse.mousePress(etapa)
            MOUSE_SOLTAR -> reprodutorMouse.mouseSoltar(etapa)
        }
    }

    private fun calcularDelay(acao: Acao, indice: Int, etapa: Etapa): Long {
        return if (indice < acao.etapas.size - 1) {
            val intervalo = acao.etapas[indice + 1].momentoExec - etapa.momentoExec
            val delay = intervalo / acao.velocidade
            delay.toLong().coerceIn(0, 999_999)

        } else 0
    }

    fun cancelar() {
        scopeExecucao.cancel("Reprodução cancelada pelo usuario")
        scopeExecucao = CoroutineScope(Job())
        acoes.clear()
        reproduzindo = false
        notificarClienteSobreFimDaReproducao()

    }

    private fun notificarClienteSobreFimDaReproducao() = scopeExecucao.launch {
        RedeController.enviar(
            comando.ipResposta,
            comando.portaResposta,
            DtoServidor(TIPO_EVENTO_SERVIDOR.ACOES_REPRODUZIDAS)
        )
    }

    private fun exibirDescricao(msg: String) {
        println("Executor: $msg")
    }


}