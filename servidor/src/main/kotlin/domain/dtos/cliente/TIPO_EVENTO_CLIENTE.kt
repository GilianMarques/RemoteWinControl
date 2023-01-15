package gmarques.remotewincontrol.domain.dtos.cliente

/**
 * Todos os tipos de informaçao que o cliente pode enviar para o servidor
 */
enum class TIPO_EVENTO_CLIENTE(val nome: String) {
    ACAO_REPRODUZIR_GRAVACAO("acao_reproduzir_gravacao"),
    ACAO_ABORTAR_GRAVACAO("acao_abortar_gravacao"),
    ACAO_PARAR_GRAVACAO("acao_parar_gravacao"),
    ACAO_GRAVAR("acao_gravar"),
    MOUSE_CLICK_ESQ("clique_mouse_esq"), // toque no mousepad e clique no botao esquerdo do mouse são sempre interpretados da mesma forma
    MOUSE_CLICK_DIR("clique_mouse_dir"),
    MOUSE_CLICK_CEN("clique_mouse_cen"),
    MOUSE_MOVE("mouse_mover"),
    MOUSE_SCROLL("mouse_scroll"),
    PAD_CLICK_TWO_FINGERS("pad_clique_dois_dedos"),
    PAD_LONG_CLICK("pad_clique_longo"),
    VOLUME_MAIS("volume_mais"),
    VOLUME_MENOS("volume_menos"),

    NONE("nenhuma_acao"),
}