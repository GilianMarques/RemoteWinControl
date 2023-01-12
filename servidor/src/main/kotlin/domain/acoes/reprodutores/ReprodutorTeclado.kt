package domain.acoes.reprodutores

import domain.reprodutores.Cmd
import domain.acoes.Etapa

class ReprodutorTeclado {

     fun tecladoPressionarTecla(etapa: Etapa) {
        val cmd = "nircmd.exe sendkey ${etapa.botao} down"
        Cmd.run(cmd)
        exibirDescricao(" ${etapa.descricao} comando: $cmd")
    }

     fun tecladoSoltarTecla(etapa: Etapa) {
        val cmd = "nircmd.exe sendkey ${etapa.botao} up"
        Cmd.run(cmd)
        exibirDescricao(" ${etapa.descricao} comando: $cmd")
    }

    private fun exibirDescricao(msg: String) {
        println("ExecutorTeclado: $msg")
    }

}
