package gmarques.remotewincontrol.presenter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import gmarques.remotewincontrol.data.AcoesDao
import gmarques.remotewincontrol.databinding.BottomSheetAcoesBinding
import gmarques.remotewincontrol.databinding.RvItemAcaoBinding
import gmarques.remotewincontrol.domain.acoes.Acao
import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteReproduzirAcao
import gmarques.remotewincontrol.rede.io.RedeController
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction0

class BottomSheetAcoes(val mostrarDialogoDeAcoes: KFunction0<Unit>) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetAcoesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        this.binding = BottomSheetAcoesBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBehavior()
        initToolbar()
        carregarAcoes()
        initFabAddAcao()
    }

    private fun initFabAddAcao() {
        binding.fabAddAcao.setOnClickListener {
            mostrarDialogoDeAcoes.invoke()
        }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { dismiss() }
    }

    private fun initBehavior() {
        val behavior = (this.dialog as BottomSheetDialog).behavior
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun carregarAcoes() {

        AcoesDao().getAcoes().forEach { acao ->
            val itemView = RvItemAcaoBinding.inflate(layoutInflater)

            itemView.fabAcao.text = acao.nome
            itemView.fabAcao.setOnClickListener { executarAcao(acao) }
            binding.container.addView(itemView.root, 0)
        }


    }

    private fun executarAcao(acao: Acao) {
        lifecycleScope.launch { RedeController.enviar(DtoClienteReproduzirAcao(acao)) }
        dismiss()

    }


}