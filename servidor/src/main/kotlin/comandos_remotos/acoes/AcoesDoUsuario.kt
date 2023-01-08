package comandos_remotos.acoes

enum class AcoesDoUsuario(val nome: String) {
    TECLADO_PRESS("teclado_pressionar"),
    TECLADO_SOLTAR("teclado_soltar"),
    MOUSE_PRESS("mouse_pressionar"),
    MOUSE_SOLTAR("mouse_soltar"),
    MOUSE_MOVER("mouse_mover"),
    MOUSE_ROLAR("mouse_rolar"),
}