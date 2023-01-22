package domain

import domain.dtos.cliente.DtoCliente
import domain.reprodutores.Cmd

object Desligamento {

    fun agendar(comando: DtoCliente) {

        val delay = comando.getInt("segundosDeAtraso")
        val mensagem = "O PC será desligado dentro de $delay segundos"
        Cmd.run("nircmd.exe initshutdown \"$mensagem\" $delay force")
        println(mensagem)
    }

    fun abortar() = Cmd.run("nircmd.exe abortshutdown")

}