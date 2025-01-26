package dev.gmarques.remotewincontrol.domain.dtos.servidor

/**
 * Todos os tipos de informaçao que o servidor pode enviar para o cliente
 */
enum class TIPO_EVENTO_SERVIDOR(val nome: String) {
    ACAO_GRAVADA("ACAO_GRAVADA"),
    ACOES_REPRODUZIDAS("ACOES_REPRODUZIDAS"),
    NONE("NONE"),

}