package gmarques.remotewincontrol.presenter.ui

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.view.View.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.data.AcoesDao
import gmarques.remotewincontrol.data.PREFS_VEL_REP_ACAO
import gmarques.remotewincontrol.data.Preferencias
import gmarques.remotewincontrol.databinding.BottomSheetAcoesBinding
import gmarques.remotewincontrol.databinding.RvItemAcaoBinding
import gmarques.remotewincontrol.domain.acoes.Acao
import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import gmarques.remotewincontrol.presenter.Vibrador
import gmarques.remotewincontrol.rede.io.RedeController
import kotlinx.coroutines.launch

class DialogoVerAcoes(
    private val fragmento: Fragment, private val mostrarDialogoDeAcoes: () -> Any,
) {


    private var velocidadeDeReproducao: Float = 1f
    private var ultimoItemComMenuExibido: RvItemAcaoBinding? = null
    private var binding: BottomSheetAcoesBinding =
            BottomSheetAcoesBinding.inflate(fragmento.layoutInflater)

    private var dialog: BottomSheetDialog

    init {
        initViews()
        dialog = BottomSheetDialog(fragmento.requireContext())
        dialog.setContentView(binding.root)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.show()
    }

    private fun initViews() {
        initToolbar()
        initViewVelocidade()
        carregarAcoes()
        initFabAddAcao()

        val transition = LayoutTransition()
        transition.setAnimateParentHierarchy(false)
        binding.container.layoutTransition = transition
    }

    @SuppressLint("SetTextI18n")
    private fun initViewVelocidade() {

        val regex = Regex("[^0-9.]")
        val atualizar = { vel: Float ->
            velocidadeDeReproducao = vel
            Preferencias().putFloat(PREFS_VEL_REP_ACAO, velocidadeDeReproducao)
            binding.tvVelocidade.text = "${velocidadeDeReproducao}x"
            Vibrador.vibInteracao()
        }

        binding.ivAdd.setOnClickListener {
            regex.replace(binding.tvVelocidade.text, "")
                .toFloat()
                .let {
                    val vel = it + 0.25f
                    if (vel <= 10f) atualizar.invoke(vel)
                }
        }

        binding.ivSub.setOnClickListener {
            regex.replace(binding.tvVelocidade.text, "")
                .toFloat()
                .let {
                    val vel = it - 0.25f
                    if (vel >= 0.25f) atualizar.invoke(vel)
                }
        }

        velocidadeDeReproducao = Preferencias().getFloat(PREFS_VEL_REP_ACAO, 1f)
        binding.tvVelocidade.text = "${velocidadeDeReproducao}x"

    }


    private fun initFabAddAcao() {
        binding.fabAddAcao.setOnClickListener {
            mostrarDialogoDeAcoes.invoke()
            dialog.dismiss()
        }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { dialog.dismiss() }
    }

    private fun carregarAcoes() {

        AcoesDao().getAcoes().forEach { acao ->
            val itemView = RvItemAcaoBinding.inflate(fragmento.layoutInflater)

            itemView.tvAcao.text = acao.nome
            itemView.tvAcao.setOnClickListener { executarAcao(acao) }
            itemView.tvAcao.setOnLongClickListener { alternarMenu(itemView) }
            itemView.ivRemover.setOnClickListener { mostrarViewConfirmarRemocao(itemView, acao) }
            itemView.ivRenomear.setOnClickListener { mostrarViewRenomearAcao(itemView, acao) }
            itemView.tvConfirmarRemocao.setOnClickListener { removerAcao(itemView, acao) }
            itemView.ivConcluir.setOnClickListener { atualizaNomeDaAcao(itemView, acao) }


            binding.container.addView(itemView.root).also {

                val params = itemView.root.layoutParams as FlexboxLayout.LayoutParams
                params.flexGrow =
                        itemView.root.findViewById<TextView>(R.id.tv_acao).text.length.toFloat()
            }

        }


    }

    private fun alternarMenu(itemView: RvItemAcaoBinding): Boolean {

        if (ultimoItemComMenuExibido != null) {
            ocultarOpcoesDaAcao(ultimoItemComMenuExibido)
            restaurarViewProEstadoPadrao(ultimoItemComMenuExibido!!)
        }

        return mostrarOpcoesDaAcao(itemView)
    }

    private fun atualizaNomeDaAcao(itemView: RvItemAcaoBinding, acao: Acao) {
        val nome = itemView.edtNome.text.toString()
            .ifEmpty { String.format(fragmento.getString(R.string.Acao_x), binding.container.childCount) }
        acao.nome = nome
        AcoesDao().salvarAcao(acao)
        itemView.tvAcao.text = nome
        restaurarViewProEstadoPadrao(itemView)
    }

    private fun restaurarViewProEstadoPadrao(itemView: RvItemAcaoBinding) {
        itemView.containerPrincipal.visibility = VISIBLE
        itemView.containerRenomear.visibility = GONE
        itemView.containerRemocao.visibility = GONE
        ocultarOpcoesDaAcao(itemView)

    }

    private fun removerAcao(itemView: RvItemAcaoBinding, acao: Acao) {
        AcoesDao().removerAcao(acao.id)
        binding.container.removeView(itemView.root)
    }

    private fun mostrarOpcoesDaAcao(itemView: RvItemAcaoBinding): Boolean {

        itemView.ivRenomear.visibility = VISIBLE
        itemView.ivRemover.visibility = VISIBLE
        itemView.div1.visibility = VISIBLE
        itemView.div2.visibility = VISIBLE

        ultimoItemComMenuExibido = itemView
        Vibrador.vibInteracao()

        return true
    }

    private fun ocultarOpcoesDaAcao(itemView: RvItemAcaoBinding?): Boolean {

        itemView?.ivRenomear?.visibility = GONE
        itemView?.ivRemover?.visibility = GONE
        itemView?.div1?.visibility = GONE
        itemView?.div2?.visibility = GONE


        return true
    }

    private fun mostrarViewRenomearAcao(itemView: RvItemAcaoBinding, acao: Acao) {
        itemView.containerRenomear.visibility = VISIBLE
        itemView.containerPrincipal.visibility = GONE
        itemView.containerRemocao.visibility = GONE
        itemView.edtNome.setText(acao.nome)
        itemView.edtNome.requestFocus()

    }

    private fun mostrarViewConfirmarRemocao(itemView: RvItemAcaoBinding, acao: Acao) {
        itemView.containerRenomear.visibility = GONE
        itemView.containerPrincipal.visibility = GONE
        itemView.containerRemocao.visibility = VISIBLE
    }

    private fun executarAcao(acao: Acao) {
        fragmento.lifecycleScope.launch {
            RedeController.enviar(DtoCliente(TIPO_EVENTO_CLIENTE.ACAO_REPRODUZIR_GRAVACAO)
                .addString("acao", acao.toJson())
                .addFloat("velocidade", velocidadeDeReproducao))
        }
        dialog.dismiss()

    }


}