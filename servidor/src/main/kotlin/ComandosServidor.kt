
/**
 * Todas as entradas que o servidor pode dar
 * Essas entradas serao enviadas para serem interpretadas no telefone
 *
 * */
enum class ComandosServidor(val nome: String) {

    ACAO_SCRIPT("acao_script"),
    NONE("n_a"),

}