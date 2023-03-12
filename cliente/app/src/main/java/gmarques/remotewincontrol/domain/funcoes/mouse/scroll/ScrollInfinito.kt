package gmarques.remotewincontrol.domain.funcoes.mouse.scroll

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gmarques.remotewincontrol.presenter.ui.ScrollAdapter
import kotlinx.coroutines.delay
import kotlin.math.abs

private const val MULTIPLICADOR = 4


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

    /** quando só tiver esse valor de view faltando pra ser exibida, mais itens
     * serao inseridos no adapter */
    private var folga = 10
    private var chamadas = 0

    /** Maximo de itens no adapter */
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
            scrollAdapter.addItens(false, folga)
            delay(10) // espero o rv carregar as views pra consultar as posiçoes
            ultimaVisivel = layoutManager.findLastVisibleItemPosition()
            primeiraVisivel = layoutManager.findFirstVisibleItemPosition()
        }

        val maxItensNaTela = ultimaVisivel - primeiraVisivel

        maxItens = maxItensNaTela * MULTIPLICADOR
        folga = maxItensNaTela / MULTIPLICADOR

    }

    /**
     * Remove o excesso de itens inseridos no adapter antes do calculo de itens maximos ser feito
     * */
    private fun removerExcedentes() {
        val excedente = scrollAdapter.itemCount - maxItens
        if (excedente > 0) scrollAdapter.removerItens(false, excedente)
    }

    /** Invocado a partir do recyclerview */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        if (faltaInicializar()) return
        if (dy > 0) scrollPraCima()
        else if (dy < 0) scrollPraBaixo()


        if (dispacharEvento(dy) && dy != 0) scrollListener.invoke(dy)

        super.onScrolled(recyclerView, dx, dy)

    }

    /**
     * Essa função serve pra reduzir a velocidade minima de rolagem que acaba sendo muito alto
     * mesmo na menor sensibilidade possivel por conta da propria interface mesmo...
     */
    private fun dispacharEvento(dy: Int): Boolean {
        val velocidade = abs(dy).coerceIn(1, 100)
        val arrasto = 40
        val relacao = (arrasto / velocidade).coerceIn(1, 15)

        //  Log.d("USUK", "ScrollInfinito.dispacharEvento: dy $dy, arrasto $arrasto  auto $relacao")
        return (chamadas % relacao == 0).also { chamadas++ }
    }

    private fun faltaInicializar() = maxItens == -1

    private fun scrollPraBaixo() {
        val primeiroItemVisivel = layoutManager.findFirstCompletelyVisibleItemPosition()

        if (primeiroItemVisivel <= folga) Handler(Looper.getMainLooper()).post {
            scrollAdapter.addItens(false, folga)

            val excedente = scrollAdapter.itemCount - maxItens
            if (excedente > 0) scrollAdapter.removerItens(true, excedente)
        }

        //Log.d("USUK", "ScrollInfinito.scrollPraBaixo: primeiroItemVisivel $primeiroItemVisivel")

    }

    private fun scrollPraCima() {
        val ultimoItemVisivel = layoutManager.findLastCompletelyVisibleItemPosition()

        if (scrollAdapter.itemCount - ultimoItemVisivel <= folga) Handler(Looper.getMainLooper()).post {
            scrollAdapter.addItens(true, folga)

            val excedente = scrollAdapter.itemCount - maxItens
            if (excedente > 0) scrollAdapter.removerItens(false, excedente)
        }

        //  Log.d("USUK", "ScrollInfinito.scrollPraCima: ultimoItemVisivel $ultimoItemVisivel")

    }
}