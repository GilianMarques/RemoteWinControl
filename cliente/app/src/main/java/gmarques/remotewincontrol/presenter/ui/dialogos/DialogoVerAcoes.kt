package gmarques.remotewincontrol.presenter.ui.dialogos

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.util.Log
import android.view.View.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.data.AcoesDao
import gmarques.remotewincontrol.databinding.DialogoVerAcoesBinding
import gmarques.remotewincontrol.databinding.ItemAcaoBinding
import gmarques.remotewincontrol.domain.acoes.Acao
import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import gmarques.remotewincontrol.presenter.Vibrador
import gmarques.remotewincontrol.rede.io.RedeController
import kotlinx.coroutines.launch

class DialogoVerAcoes(
    private val fragmento: Fragment,
    private val mostrarDialogoDeAcoes: () -> Any,
) {


    private var ultimoItemComMenuExibido: ItemAcaoBinding? = null
    private var binding: DialogoVerAcoesBinding =
            DialogoVerAcoesBinding.inflate(fragmento.layoutInflater)

    private var dialog: BottomSheetDialog
    private var acoesDao = AcoesDao()

    init {
        initViews()
        dialog = BottomSheetDialog(fragmento.requireContext())
        dialog.setContentView(binding.root)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.show()
    }

    private fun initViews() {
        initToolbar()
        carregarAcoes()
        initFabAddAcao()

        val transition = LayoutTransition()
        transition.setAnimateParentHierarchy(false)
        binding.container.layoutTransition = transition
    }

    @SuppressLint("SetTextI18n")


    private fun initFabAddAcao() {
        binding.btnGravar.setOnClickListener {
            mostrarDialogoDeAcoes.invoke()
            dialog.dismiss()
        }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { dialog.dismiss() }
    }

    private fun carregarAcoes() {

        val acoes = acoesDao.getAcoes()
        acoes.sortByDescending { it.nome }
        acoes.forEach { acao ->

            val itemView = ItemAcaoBinding.inflate(fragmento.layoutInflater)

            itemView.ivFecharMenu.setOnClickListener { ocultarOpcoesDaAcao(itemView) }

            itemView.tvAcao.text = acao.nome
            itemView.tvAcao.tag = acao.id

            itemView.tvAcao.setOnClickListener { executarAcao(acao) }
            itemView.tvAcao.setOnLongClickListener { alternarMenu(itemView) }

            itemView.ivRemover.setOnClickListener { mostrarViewConfirmarRemocao(itemView, acao) }
            itemView.tvConfirmarRemocao.setOnClickListener { removerAcao(itemView, acao) }

            itemView.ivRenomear.setOnClickListener { mostrarViewRenomearAcao(itemView, acao) }
            itemView.ivSalvarRenomear.setOnClickListener { atualizaNomeDaAcao(itemView, acao) }

            itemView.ivVelocidade.setOnClickListener { mostrarViewVelocidade(itemView, acao) }
            itemView.ivAplicarVelocidade.setOnClickListener { salvarNovaVelocidade(itemView, acao) }


            binding.container.addView(itemView.root).also {
                Log.d("USUK", "DialogoVerAcoes.carregarAcoes: ${acao.nome} ${acao.posicao}")

                val params = itemView.root.layoutParams as FlexboxLayout.LayoutParams
                params.flexGrow =
                        itemView.root.findViewById<TextView>(R.id.tv_acao).text.length.toFloat()
            }

            //garante que açoes sem um posiçao definida pelo usuario recebam uma posiçao de acordo com sua posiçao na tela
            acao.posicao = binding.container.childCount - 1

        }


    }


    private fun alternarMenu(itemView: ItemAcaoBinding): Boolean {

        if (ultimoItemComMenuExibido != null) {
            ocultarOpcoesDaAcao(ultimoItemComMenuExibido)
            restaurarViewProEstadoPadrao(ultimoItemComMenuExibido!!)
        }

        return mostrarOpcoesDaAcao(itemView)
    }

    private fun atualizaNomeDaAcao(itemView: ItemAcaoBinding, acao: Acao) {
        val nome = itemView.edtNome.text.toString()
            .ifEmpty { String.format(fragmento.getString(R.string.Acao_x), binding.container.childCount) }
        acao.nome = nome
        acoesDao.salvarAcao(acao)
        itemView.tvAcao.text = nome
        restaurarViewProEstadoPadrao(itemView)
    }

    private fun restaurarViewProEstadoPadrao(itemView: ItemAcaoBinding) {
        itemView.containerPrincipal.visibility = VISIBLE
        itemView.containerRenomear.visibility = GONE
        itemView.containerRemocao.visibility = GONE
        itemView.containerVelocidade.visibility = GONE
        ocultarOpcoesDaAcao(itemView)

    }

    private fun removerAcao(itemView: ItemAcaoBinding, acao: Acao) {
        acoesDao.removerAcao(acao.id)
        binding.container.removeView(itemView.root)
    }

    private fun mostrarOpcoesDaAcao(itemView: ItemAcaoBinding): Boolean {

        itemView.containerPrincipal.visibility = GONE
        itemView.containerOpcoes.visibility = VISIBLE

        ultimoItemComMenuExibido = itemView
        Vibrador.vibInteracao()

        return true
    }

    private fun ocultarOpcoesDaAcao(itemView: ItemAcaoBinding?): Boolean {
        itemView?.containerPrincipal?.visibility = VISIBLE
        itemView?.containerOpcoes?.visibility = GONE
        return true
    }

    private fun mostrarViewRenomearAcao(itemView: ItemAcaoBinding, acao: Acao) {
        itemView.containerRenomear.visibility = VISIBLE
        itemView.containerPrincipal.visibility = GONE
        itemView.containerOpcoes.visibility = GONE
        itemView.containerRemocao.visibility = GONE
        itemView.edtNome.setText(acao.nome)
        itemView.edtNome.requestFocus()

    }

    @SuppressLint("SetTextI18n")
    private fun mostrarViewVelocidade(itemView: ItemAcaoBinding, acao: Acao) {
        itemView.containerVelocidade.visibility = VISIBLE
        itemView.containerPrincipal.visibility = GONE
        itemView.containerOpcoes.visibility = GONE
        itemView.containerRenomear.visibility = GONE
        itemView.containerRemocao.visibility = GONE
        itemView.edtVelocidade.setText("${acao.velocidade}x".replace(".0x", "x"))
        itemView.edtVelocidade.requestFocus()

    }

    private fun salvarNovaVelocidade(itemView: ItemAcaoBinding, acao: Acao) {
        Regex("[^0-9.]")
            .replace(itemView.edtVelocidade.text.toString(), "")
            .ifEmpty { "1" }
            .apply {
                val velocidade = toFloat().coerceIn(Acao.velocidadeMinima, Acao.velocidadeMaxima)
                acao.velocidade = velocidade
                itemView.edtVelocidade.setText("${velocidade}x")
                acoesDao.salvarAcao(acao)
                restaurarViewProEstadoPadrao(itemView)

            }

    }

    private fun mostrarViewConfirmarRemocao(itemView: ItemAcaoBinding, acao: Acao) {
        itemView.containerOpcoes.visibility = GONE
        itemView.containerRenomear.visibility = GONE
        itemView.containerPrincipal.visibility = GONE
        itemView.containerRemocao.visibility = VISIBLE
    }

    private fun executarAcao(acao: Acao) {
        fragmento.lifecycleScope.launch {
            RedeController.enviar(
                DtoCliente(TIPO_EVENTO_CLIENTE.ACAO_REPRODUZIR_GRAVACAO)
                    .addString("acao", acao.toJson())
            )
        }
        dialog.dismiss()

    }


}


