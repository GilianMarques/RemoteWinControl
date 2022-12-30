package gmarques.remotewincontrol.domain

enum class GestureType(nome: String) {
    CLICK("clique"),
    CLICK_TWO_FINGERS("clique_dois_dedos"),
    LONG_CLICK("clique_longo"),
    PRESS("pressionar"),
    RELEASE("soltar"),
    MOVE("mover"),
    NONE("n-a"),
}