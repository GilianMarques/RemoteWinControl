package gmarques.remotewincontrol.presenter

/**
 * Todas as entradas que o usuario pode dar
 * Essas entradas serao enviadas para serem interpretadas no servidor
 *
 * */
enum class EntradaUsuario(val nome: String) {
    MOUSE_CLICK_ESQ("clique_mouse_esq"), // toque no mousepad e clique no botao esquerdo do mouse são sempre interpretados da mesma forma
    MOUSE_CLICK_DIR("clique_mouse_dir"),
    MOUSE_CLICK_CEN("clique_mouse_cen"),
    PAD_CLICK_TWO_FINGERS("pad_clique_dois_dedos"),
    PAD_LONG_CLICK("pad_clique_longo"),
    VOLUME_MAIS("volume_mais"),
    VOLUME_MENOS("volume_menos"),
    PAD_MOVE("pad_mover"),
    SCROLL("scroll"),
    NONE("n_a"),
}