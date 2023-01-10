package gmarques.remotewincontrol.rede.dtos.cliente

/**
 * Todos os tipos de informaçao que o cliente pode enviar para o servidor
 */
enum class TIPO_DTO_CLIENTE(val nome: String) {
    ACAO_PARAR_GRAVACAO("acao_parar_gravacao"),
    ACAO_GRAVAR("acao_gravar"),
    MOUSE_CLICK_ESQ("clique_mouse_esq"), // toque no mousepad e clique no botao esquerdo do mouse são sempre interpretados da mesma forma
    MOUSE_CLICK_DIR("clique_mouse_dir"),
    MOUSE_CLICK_CEN("clique_mouse_cen"),
    PAD_CLICK_TWO_FINGERS("pad_clique_dois_dedos"),
    PAD_LONG_CLICK("pad_clique_longo"),
    VOLUME_MAIS("volume_mais"),
    VOLUME_MENOS("volume_menos"),
    PAD_MOVE("pad_mover"),
    SCROLL("scroll"),
    NONE("nenhuma_acao"),
}