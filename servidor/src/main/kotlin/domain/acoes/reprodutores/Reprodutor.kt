package domain.acoes.reprodutores

import domain.acoes.Acao
import domain.acoes.Etapa
import domain.acoes.Etapa.TIPO.*
import kotlinx.coroutines.*
import rede.JsonMapper

object Reprodutor {

    private var velocidade = 1f
    private val scopeExecucao = CoroutineScope(Job())
    private val reprodutorMouse = ReprodutorMouse()
    private val reprodutorTeclado = ReprodutorTeclado()
    private lateinit var acao: Acao

    fun executar(acao: Acao, velocidade: Float) = scopeExecucao.launch {
        this@Reprodutor.velocidade = velocidade
        this@Reprodutor.acao = acao
        exibirDescricao("executando ação: ${JsonMapper.toJson(acao.etapas)}")
        iterarSobreEtapas()
        exibirDescricao("ação executada")
    }

    fun cancelar() {
        scopeExecucao.cancel("Açao cancelada pelo usuario")
    }

    private suspend fun iterarSobreEtapas() {
        for (i in 0 until acao.etapas.size) {
            val etapa = acao.etapas[i]
            avaliarAcao(etapa)

            if (i < acao.etapas.size - 1) {
                val intervalo = acao.etapas[i + 1].momentoExec - etapa.momentoExec
                val delay = if (velocidade < 1f) intervalo / velocidade else intervalo / velocidade
                val delayTratado = delay.toLong().coerceIn(1, 60_000)
                println("esperando ${delayTratado}mls delay orginal ${intervalo}mls vel: $velocidade")
                delay(delayTratado)

            }
        }
    }

    private fun avaliarAcao(etapa: Etapa) {
        when (etapa.tipo) {
            TECLADO_PRESS -> reprodutorTeclado.tecladoPressionarTecla(etapa)
            TECLADO_SOLTAR -> reprodutorTeclado.tecladoSoltarTecla(etapa)
            MOUSE_MOVER -> reprodutorMouse.mouseMover(etapa)
            MOUSE_ROLAR -> reprodutorMouse.mouseRolar(etapa)
            MOUSE_PRESS -> reprodutorMouse.mousePress(etapa)
            MOUSE_SOLTAR -> reprodutorMouse.mouseSoltar(etapa)
        }
    }

    private fun exibirDescricao(msg: String) {
        println("Executor: $msg")
    }


}