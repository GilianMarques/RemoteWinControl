package gmarques.remotewincontrol.presenter.ui.dialogos

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.data.AcoesDao
import gmarques.remotewincontrol.databinding.BottomSheetVerAcoesBinding
import gmarques.remotewincontrol.databinding.FragmentMainBinding
import gmarques.remotewincontrol.databinding.ItemAcaoBinding
import gmarques.remotewincontrol.domain.acoes.Acao
import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import gmarques.remotewincontrol.domain.dtos.servidor.DtoServidor
import gmarques.remotewincontrol.domain.dtos.servidor.TIPO_EVENTO_SERVIDOR
import gmarques.remotewincontrol.presenter.Vibrador
import gmarques.remotewincontrol.rede.io.RedeController
import kotlinx.coroutines.launch

// TODO: refatorar essa classe

class BottomSheetVerAcoes(
    private val fragmento: Fragment,
    private val fragmentBinding: FragmentMainBinding,
    private val mostrarDialogoDeAcoes: () -> Any,
) : RedeController.RedeCallback {


    private val binding: BottomSheetVerAcoesBinding = fragmentBinding.includeBottomSheet
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var ultimoItemComMenuExibido: ItemAcaoBinding? = null
    private var acoesDao = AcoesDao()

    init {

        initBottomSheetBehavior()
        initViews()
        addListenerDeRede()

    }

    private fun initBottomSheetBehavior() {

        val bottomSheetView = binding.parentContainer
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        bottomSheetBehavior.isHideable = false

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        fragmentBinding.fabAcoes.visibility = VISIBLE
                    }

                    else -> fragmentBinding.fabAcoes.visibility = GONE

                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    fun atualizar() {
        binding.container.removeAllViews()
        carregarAcoes()
    }

    fun exibir() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun initViews() {
        carregarAcoes()
        initBotoesAddEParar()

        val transition = LayoutTransition()
        transition.setAnimateParentHierarchy(false)
        binding.container.layoutTransition = transition
    }

    private fun initBotoesAddEParar() {
        binding.btnGravar.setOnClickListener {
            mostrarDialogoDeAcoes.invoke()
            ocultarBottomSheet()

        }

        binding.btnPararReproducao.setOnClickListener {
            pararReproducao()
        }
    }

    private fun carregarAcoes() = fragmento.lifecycleScope.launch {

        val acoes = acoesDao.getAcoes()
        acoes.sortBy { it.nome }
        acoes.forEach { acao ->

            val itemView = ItemAcaoBinding.inflate(fragmento.layoutInflater)

            itemView.ivFecharMenu.setOnClickListener { ocultarOpcoesDaAcao(itemView) }

            itemView.tvAcao.text = acao.nome
            itemView.tvAcao.tag = acao.id

            itemView.tvAcao.setOnClickListener { executarAcao(acao, false) }
            itemView.tvAcao.setOnLongClickListener { executarAcao(acao, true) }

            itemView.ivMenu.setOnClickListener { mostrarMenu(itemView) }

            itemView.ivRemover.setOnClickListener { mostrarViewConfirmarRemocao(itemView, acao) }
            itemView.tvConfirmarRemocao.setOnClickListener { removerAcao(itemView, acao) }

            itemView.ivRenomear.setOnClickListener { mostrarViewRenomearAcao(itemView, acao) }
            itemView.ivSalvarRenomear.setOnClickListener { atualizaNomeDaAcao(itemView, acao) }

            itemView.ivVelocidade.setOnClickListener { mostrarViewVelocidade(itemView, acao) }
            itemView.ivAplicarVelocidade.setOnClickListener { salvarNovaVelocidade(itemView, acao) }

            binding.container.addView(itemView.root)
        }


    }

    private fun mostrarMenu(itemView: ItemAcaoBinding): Boolean {

        if (ultimoItemComMenuExibido != null) {
            exibirApenasContainerEspecifico(
                ultimoItemComMenuExibido, ultimoItemComMenuExibido?.containerPrincipal
            )
        }

        return mostrarOpcoesDaAcao(itemView)
    }

    private fun atualizaNomeDaAcao(itemView: ItemAcaoBinding, acao: Acao) {
        val nome = itemView.edtNome.text.toString().ifEmpty {
            String.format(
                fragmento.getString(R.string.Acao_x), binding.container.childCount
            )
        }
        acao.nome = nome
        acoesDao.salvarAcao(acao)
        itemView.tvAcao.text = nome
        restaurarViewProEstadoPadrao(itemView)
    }

    private fun restaurarViewProEstadoPadrao(itemView: ItemAcaoBinding) {
        exibirApenasContainerEspecifico(itemView, itemView.containerPrincipal)
    }

    private fun removerAcao(itemView: ItemAcaoBinding, acao: Acao) {
        acoesDao.removerAcao(acao.id)
        binding.container.removeView(itemView.root)
    }

    private fun mostrarOpcoesDaAcao(itemView: ItemAcaoBinding): Boolean {

        exibirApenasContainerEspecifico(itemView, itemView.containerOpcoes)
        ultimoItemComMenuExibido = itemView
        Vibrador.vibInteracao()

        return true
    }

    private fun ocultarOpcoesDaAcao(itemView: ItemAcaoBinding?): Boolean {
        exibirApenasContainerEspecifico(itemView, itemView?.containerPrincipal)
        return true
    }

    private fun mostrarViewRenomearAcao(itemView: ItemAcaoBinding, acao: Acao) {
        exibirApenasContainerEspecifico(itemView, itemView.containerRenomear)
        itemView.edtNome.setText(acao.nome)
        itemView.edtNome.requestFocus()

    }

    @SuppressLint("SetTextI18n")
    private fun mostrarViewVelocidade(itemView: ItemAcaoBinding, acao: Acao) {

        exibirApenasContainerEspecifico(itemView, itemView.containerVelocidade)

        itemView.edtVelocidade.setText("${acao.velocidade}x".replace(".0x", "x"))
        itemView.edtVelocidade.requestFocus()

    }

    private fun salvarNovaVelocidade(itemView: ItemAcaoBinding, acao: Acao) {
        Regex("[^0-9.]").replace(itemView.edtVelocidade.text.toString(), "").ifEmpty { "1" }.apply {
            val velocidade = toFloat().coerceIn(Acao.velocidadeMinima, Acao.velocidadeMaxima)
            acao.velocidade = velocidade
            itemView.edtVelocidade.setText("${velocidade}x")
            acoesDao.salvarAcao(acao)
            restaurarViewProEstadoPadrao(itemView)

        }

    }

    private fun mostrarViewConfirmarRemocao(itemView: ItemAcaoBinding, acao: Acao) {
        exibirApenasContainerEspecifico(itemView, itemView.containerRemocao)

    }

    private fun exibirApenasContainerEspecifico(itemView: ItemAcaoBinding?, alvo: ViewGroup?) {
        itemView?.itemViewContainer?.forEach {
            it.visibility = if (alvo?.id == it.id) VISIBLE else GONE
        }
    }

    private fun executarAcao(acao: Acao, dispensarDialogo: Boolean): Boolean {

        fragmento.lifecycleScope.launch {
            val ordemEnviada = RedeController.enviar(
                DtoCliente(TIPO_EVENTO_CLIENTE.ACAO_REPRODUZIR_GRAVACAO).addString(
                    "acao",
                    acao.toJson()
                )
            )
            if (ordemEnviada) mostrarBotaoPararReproducao(true)

        }

        Vibrador.vibInteracao()

        if (dispensarDialogo) ocultarBottomSheet()

        return true
    }

    private fun ocultarBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }

    private fun pararReproducao() {

        fragmento.lifecycleScope.launch {
            val ordemEnviada = RedeController.enviar(
                DtoCliente(TIPO_EVENTO_CLIENTE.ACAO_PARAR_REPRODUCAO)
            )

            if (ordemEnviada) Vibrador.vibInteracao()
        }
    }

    private fun addListenerDeRede() {
        RedeController.addListener(TIPO_EVENTO_SERVIDOR.ACOES_REPRODUZIDAS, this)
        Log.d("USUK", "DialogoVerAcoes.addListenerDeRede: ")
    }

    private fun mostrarBotaoPararReproducao(mostar: Boolean) {

        binding.btnPararReproducao.visibility = if (mostar) VISIBLE else INVISIBLE
     //   binding.btnGravar.visibility = if (!mostar) VISIBLE else INVISIBLE

    }

    /** callback do listener de servidor
     * @see addListenerDeRede
     * */
    override fun eventoRecebido(comandoJson: DtoServidor): Boolean {
        mostrarBotaoPararReproducao(false)
        return false
    }


}



