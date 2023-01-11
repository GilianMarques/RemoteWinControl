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
import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteReproduzirAcao
import gmarques.remotewincontrol.rede.dtos.cliente.DtoClienteSemMetadata
import gmarques.remotewincontrol.rede.io.RedeAdapter
import kotlinx.coroutines.launch

class BottomSheetAcoes : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetAcoesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        this.binding = BottomSheetAcoesBinding.inflate(inflater)
        return binding.root
    }

    // TODO: otimizar essa classe
    // TODO:remover bottomsheet e por acoes na tela msm
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // isCancelable = false

        val behavior = (this.dialog as BottomSheetDialog).behavior
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        carregarAcoes()
    }

    private fun carregarAcoes() {
        AcoesDao().getAcoes().forEach { acao ->
            val itemView = RvItemAcaoBinding.inflate(layoutInflater)
            itemView.fabAcao.text = acao.nome
            itemView.fabAcao.setOnClickListener {
                lifecycleScope.launch { RedeAdapter.enviar(DtoClienteReproduzirAcao(acao.etapas)) }
            }

            binding.container.addView(itemView.root)
        }

    }


}