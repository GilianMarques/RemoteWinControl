package gmarques.remotewincontrol.presenter.mouse.scroll

/**
 * Aplica as regras do scroll
 */
object ScrollHandler {

    @Suppress("UnnecessaryVariable")
    fun getMetadata(direcao: Int): Float {

        val direcaoInvertida = (direcao * -1).toFloat()
        val linhasRoladas = direcaoInvertida.coerceIn(-1f, 1f)

        return linhasRoladas

    }

}