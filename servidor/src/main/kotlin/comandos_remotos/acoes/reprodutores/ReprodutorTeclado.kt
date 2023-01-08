package comandos_remotos.acoes.reprodutores

import comandos_remotos.Cmd
import comandos_remotos.acoes.Etapa

object ReprodutorTeclado {

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
