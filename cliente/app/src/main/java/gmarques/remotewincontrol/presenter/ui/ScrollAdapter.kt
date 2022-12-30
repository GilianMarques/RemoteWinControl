package gmarques.remotewincontrol.presenter.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gmarques.remotewincontrol.databinding.RvItemScrollBinding

class ScrollAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<ScrollAdapter.Holder>() {

    private val itens = ArrayList<Int>()

    inner class Holder(private val item: RvItemScrollBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(indice: Int) {

            val intervalo = 3
            val condicao = indice % intervalo == 0

            item.indicadorDestaque.visibility = if (condicao) View.VISIBLE else View.INVISIBLE
            item.indicadorPadrao.visibility = if (condicao) View.INVISIBLE else View.VISIBLE

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(RvItemScrollBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: Holder, indice: Int) = holder.bind(indice)

    override fun getItemCount() = itens.size

    /**
     * Adiciona itens no começo ou final da lista para gerar o efeito de scroll infinito no recyclerview
     * Chame essa função dentro de um Handler(Looper.getMainLooper()).post {}/Coroutine para evitar bugs no
     * recyclerView ao chamar notifyItem... no adapter enquanto o recyclerview esta sendo escrolado
     *
     * @param noFinalDaLista onde adicionar novos itens
     * @param quantidade quantidade de itens a adicionar
     *
     * @see removerExcesso
     *
     *  */
    fun addItens(noFinalDaLista: Boolean, quantidade: Int) {
        repeat(quantidade) {
            if (noFinalDaLista) {
                itens.add(0)
                notifyItemInserted(itens.size)
            } else {
                itens.add(0, 0)
                notifyItemInserted(0)
            }
        }
    }

    /**
     * remove itens do começo ou final da lista para que o efeito de scroll infinito no recyclerview
     * nao consuma mais memoria que o necessario.
     *
     * Chame essa função dentro de um Handler(Looper.getMainLooper()).post {}/Coroutine para evitar bugs no
     * recyclerView ao chamar notifyItem... no adapter enquanto o recyclerview esta sendo escrolado
     *
     * @param doFinalDaFila de onde remover os itens
     * @param excedente quantidade de itens a ser removida
     *
     * @see addItens
     */
    fun removerExcesso(doFinalDaFila: Boolean, excedente: Int) {
        if (doFinalDaFila) {
            repeat(excedente) {
                itens.removeAt(itens.size - 1)
                notifyItemRemoved(itens.size - 1)
                /*
                 * tentar usar o 'notifyItemRangeRemoved()' no funcionou por que
                 * ao notificar, o scroll do recyclerView se move ate que a posicao 0 estaja visivel quebrando a logica
                 */
            }

        } else {
            repeat(excedente) { itens.removeAt(0) }
            notifyItemRangeRemoved(0, excedente)
        }
    }
}