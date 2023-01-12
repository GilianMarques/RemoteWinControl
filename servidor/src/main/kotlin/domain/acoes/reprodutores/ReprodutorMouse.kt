package domain.acoes.reprodutores

import domain.reprodutores.Cmd
import domain.acoes.Etapa
import java.awt.Robot

class ReprodutorMouse {

    private val mouse = Robot()
    private val ultimaEtapaExecutada: Etapa? = null

    fun mouseSoltar(etapa: Etapa) {
        val botao = when (etapa.botao) {
            1 -> "left"
            2 -> "right"
            3 -> "middle"
            else -> "?"
        }

        val cmd = "nircmd.exe sendmouse $botao up"
        Cmd.run(cmd)
        exibirDescricao(" ${etapa.descricao} comando: $cmd")
    }

    fun mousePress(etapa: Etapa) {

        val botao = when (etapa.botao) {
            1 -> "left"
            2 -> "right"
            3 -> "middle"
            else -> "?"
        }

        val cmd = "nircmd.exe sendmouse $botao down"
        Cmd.run(cmd)
        exibirDescricao(" ${etapa.descricao} comando: $cmd")
    }

    fun mouseRolar(etapa: Etapa) {
        mouse.mouseWheel(etapa.rolarDirecao)
        exibirDescricao(" ${etapa.descricao}")
    }

    fun mouseMover(etapa: Etapa) {

        mouse.mouseMove(etapa.movX, etapa.movY)
        exibirDescricao(" ${etapa.descricao} comando:")
    }

    private fun exibirDescricao(msg: String) {
        println("ExecutorMouse: $msg")
    }


}
