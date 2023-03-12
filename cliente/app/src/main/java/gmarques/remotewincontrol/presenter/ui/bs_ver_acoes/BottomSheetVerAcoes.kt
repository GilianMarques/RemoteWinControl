package gmarques.remotewincontrol.presenter.ui.bs_ver_acoes

import android.animation.LayoutTransition
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import gmarques.remotewincontrol.data.AcoesDao
import gmarques.remotewincontrol.databinding.BottomSheetVerAcoesBinding
import gmarques.remotewincontrol.databinding.FragmentMainBinding
import gmarques.remotewincontrol.databinding.ItemAcaoBinding
import gmarques.remotewincontrol.domain.funcoes.acoes.Acao
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


            itemView.tvAcao.text = acao.nome

            itemView.tvAcao.setOnClickListener { executarAcao(acao, false) }
            itemView.tvAcao.setOnLongClickListener { executarAcao(acao, true) }

            itemView.ivMenu.setOnClickListener { mostrarMenu(acao) }

            binding.container.addView(itemView.root)
        }


    }

    private fun mostrarMenu(acao: Acao) {
        DialogoEditarAcao(acao, fragmento) { atualizar() }
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



