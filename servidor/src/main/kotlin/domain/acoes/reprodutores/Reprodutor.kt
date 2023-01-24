package domain.acoes.reprodutores

import domain.acoes.Acao
import domain.acoes.Etapa
import domain.acoes.Etapa.TIPO.*
import kotlinx.coroutines.*
import rede.JsonMapper

object Reprodutor {

    private val scopeExecucao = CoroutineScope(Job())
    private val reprodutorMouse = ReprodutorMouse()
    private val reprodutorTeclado = ReprodutorTeclado()
    private lateinit var acao: Acao

    fun executar(acao: Acao) = scopeExecucao.launch {
        this@Reprodutor.acao = acao
        exibirDescricao("executando ação: '${acao.nome}' em ${acao.velocidade}x")
        iterarSobreEtapas()
        exibirDescricao("ação executada")
    }

    fun cancelar() {
        scopeExecucao.cancel("Açao cancelada pelo usuario")
    }

    private suspend fun iterarSobreEtapas() {
        for (i in 0 until acao.etapas.size) {
            val etapa = acao.etapas[i]
            executarAcao(etapa)
            delay(calcularDelay(i, etapa))
        }
    }

    private fun calcularDelay(indice: Int, etapa: Etapa): Long {
        return if (indice < acao.etapas.size - 1) {
            val intervalo = acao.etapas[indice + 1].momentoExec - etapa.momentoExec
            val delay = intervalo / acao.velocidade
            delay.toLong().coerceIn(0, 60_000)

        } else 0
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

    private fun exibirDescricao(msg: String) {
        println("Executor: $msg")
    }


}