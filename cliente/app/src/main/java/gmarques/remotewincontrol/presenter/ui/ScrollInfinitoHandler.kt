package gmarques.remotewincontrol.presenter.ui

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay

private const val MULTIPLICADOR = 4

/** quando só tiver esse valor de view faltando pra ser exibida, mais itens
 * serao inseridos no adapter */
private const val FOLGA = 50


/**
 * chame  inicializar(), esse é o ponto de entrada da classe
 * Defina essa classe como listener de scroll do recyclerview alvo
 * @See inicializar
 */
class ScrollInfinito(
    private val layoutManager: LinearLayoutManager,
    private val scrollAdapter: ScrollAdapter,
) : RecyclerView.OnScrollListener() {

    lateinit var scrollListener: (direcao: Int) -> Any

    /**maximo de itens no adapter*/
    var maxItens = -1

    suspend fun inicializar(callback: (() -> Unit)? = null) {

        calcularMaxItens()
        removerExcedentes()

        // adiciono um item no inicio/final da lista pra garantir que é possivel escrolar pra ambos os lados, acionando o listener
        scrollAdapter.addItens(true, 1)
        scrollAdapter.addItens(false, 1)

        callback?.invoke()
    }

    /**
     * Calcula a quantidade maxima de itens do adapter com base em quantas
     * views cabem na tela do dispositivo
     * */
    private suspend fun calcularMaxItens() {

        var ultimaVisivel = 0
        var primeiraVisivel = 0

        while (primeiraVisivel <= 0) {
            scrollAdapter.addItens(false, FOLGA)
            delay(10) // espero o rv carregar as views pra consultar as posiçoes
            ultimaVisivel = layoutManager.findLastVisibleItemPosition()
            primeiraVisivel = layoutManager.findFirstVisibleItemPosition()
        }

        val maxItensNaTela = ultimaVisivel - primeiraVisivel

        maxItens = maxItensNaTela * MULTIPLICADOR

    }

    /**
     * Remove o excesso de itens inseridos no adapter antes do calculo de itens maximos ser feito
     * */
    private fun removerExcedentes() {
        val excedente = scrollAdapter.itemCount - maxItens
        if (excedente > 0) scrollAdapter.removerExcesso(false, excedente)
    }

    /**Invocado a partir do recyclerview*/
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (maxItens == -1) return // classe nao inicializada
        if (dy > 0) scrollPraCima()
        else scrollPraBaixo()

        scrollListener.invoke(dy)

        super.onScrolled(recyclerView, dx, dy)

    }

    private fun scrollPraBaixo() {
        val primeiroItemVisivel = layoutManager.findFirstCompletelyVisibleItemPosition()

        if (primeiroItemVisivel <= FOLGA) Handler(Looper.getMainLooper()).post {
            scrollAdapter.addItens(false, FOLGA)

            val excedente = scrollAdapter.itemCount - maxItens
            if (excedente > 0) scrollAdapter.removerExcesso(true, excedente)
        }

        Log.d("USUK", "ScrollInfinito.scrollPraBaixo: primeiroItemVisivel $primeiroItemVisivel")

    }

    private fun scrollPraCima() {
        val ultimoItemVisivel = layoutManager.findLastCompletelyVisibleItemPosition()

        if (scrollAdapter.itemCount - ultimoItemVisivel <= FOLGA) Handler(Looper.getMainLooper()).post {
            scrollAdapter.addItens(true, FOLGA)

            val excedente = scrollAdapter.itemCount - maxItens
            if (excedente > 0) scrollAdapter.removerExcesso(false, excedente)
        }

        Log.d("USUK", "ScrollInfinito.scrollPraCima: ultimoItemVisivel $ultimoItemVisivel")

    }
}