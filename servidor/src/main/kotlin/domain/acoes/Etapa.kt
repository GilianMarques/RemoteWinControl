package domain.acoes

data class Etapa(
    val momentoExec: Long,
    val tipo: TIPO,
) {
    var botao: Int = -1
    var movX: Int = -1
    var movY: Int = -1
    var rolarDirecao: Int = -1
    var descricao: String = ""

    enum class TIPO(val nome: String) {
        TECLADO_PRESS("teclado_pressionar"),
        TECLADO_SOLTAR("teclado_soltar"),
        MOUSE_PRESS("mouse_pressionar"),
        MOUSE_SOLTAR("mouse_soltar"),
        MOUSE_MOVER("mouse_mover"),
        MOUSE_ROLAR("mouse_rolar"),
    }
}
