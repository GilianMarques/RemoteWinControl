package comandos_remotos.acoes.reprodutores

import com.google.gson.Gson
import comandos_remotos.acoes.AcoesDoUsuario
import comandos_remotos.acoes.Etapa
import kotlinx.coroutines.*

class Reprodutor(private val acao: ArrayList<Etapa>) {

    private val aceleracao = 1
    private val scopeExecucao = CoroutineScope(Job())

    fun executar() = scopeExecucao.launch {
        exibirDescricao("executando ação: ${Gson().toJson(acao)}")
        iterarSobreEtapas()
        exibirDescricao("ação executada")
    }

    fun cancelar() {
        scopeExecucao.cancel("Açao cancelada pelo usuario")
    }

    private suspend fun iterarSobreEtapas() {
        for (i in 0 until acao.size) {
            val etapa = acao[i]
            avaliarAcao(etapa)
            if (i < acao.size - 1) delay((acao[i + 1].momentoExec - etapa.momentoExec) / aceleracao)

        }
    }

    private fun avaliarAcao(etapa: Etapa) {
        when (etapa.tipo) {
            AcoesDoUsuario.TECLADO_PRESS -> ReprodutorTeclado.tecladoPressionarTecla(etapa)
            AcoesDoUsuario.TECLADO_SOLTAR -> ReprodutorTeclado.tecladoSoltarTecla(etapa)
            AcoesDoUsuario.MOUSE_MOVER -> ReprodutorMouse.mouseMover(etapa)
            AcoesDoUsuario.MOUSE_ROLAR -> ReprodutorMouse.mouseRolar(etapa)
            AcoesDoUsuario.MOUSE_PRESS -> ReprodutorMouse.mousePress(etapa)
            AcoesDoUsuario.MOUSE_SOLTAR -> ReprodutorMouse.mouseSoltar(etapa)
        }
    }

    private fun exibirDescricao(msg: String) {
        println("Executor: $msg")
    }



}