package gmarques.remotewincontrol.presenter.mouse.scroll

/**
 * Aplica as regras do scroll
 */
object ScrollHandler {

    @Suppress("UnnecessaryVariable")
    fun getMetadata(direcao: Int): Int {

        val direcaoInvertida = direcao * -1
        val linhasRoladas = direcaoInvertida.coerceIn(-1, 1)

        return linhasRoladas

    }

}