package comandos_remotos.acoes

import ComandosUsuario

data class Etapa(
    val momentoExec: Long,
    val tipo: AcoesDoUsuario,
) {
    var botao: Int = -1
    var movX: Int = -1
    var movY: Int = -1
    var rolarDirecao: Int = -1
    var descricao: String = ""
}
