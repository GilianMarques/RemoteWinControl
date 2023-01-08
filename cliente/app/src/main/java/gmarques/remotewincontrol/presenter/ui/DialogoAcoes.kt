package gmarques.remotewincontrol.presenter.ui

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import gmarques.remotewincontrol.databinding.DialogoAcoesBinding
import gmarques.remotewincontrol.presenter.acoes.Acoes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DialogoAcoes(fragmento: Fragment, private val callback: Callback) {

    private var binding = DialogoAcoesBinding.inflate(fragmento.layoutInflater)
    private var dialog: BottomSheetDialog
    private val acoes = Acoes()
    private val scope: CoroutineScope

    init {
        scope = fragmento.lifecycleScope
        initViews()
        dialog = BottomSheetDialog(fragmento.requireContext())
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.show()
    }

    private fun initViews() {

        binding.btnGravar.setOnClickListener { iniciarGravacao() }

        binding.btnPararGravacao.setOnClickListener { pararGravacao() }

        binding.btnSalvar.setOnClickListener {
            if (nomeValido()) acoes.salvarAcao(binding.edtNome.text.toString())
            dialog.dismiss()
        }

        binding.btnCancelar.setOnClickListener {
            dialog.dismiss()
        }
        binding.toolbar.setNavigationOnClickListener {
            dialog.dismiss()
        }

    }

    private fun pararGravacao() = scope.launch {
        if (acoes.pararGravacao()) {
            binding.btnPararGravacao.visibility = View.GONE
            binding.btnSalvar.visibility = View.VISIBLE // TODO: so mostarr esse botao depois de receber a acao
            binding.inputNome.visibility = View.VISIBLE
            binding.inputNome.requestFocus()


        }
    }

    private fun iniciarGravacao() = scope.launch {
        if (acoes.iniciarGravacao()) {
            binding.btnPararGravacao.visibility = View.VISIBLE
            binding.btnGravar.visibility = View.GONE
        }
    }

    private fun nomeValido() = binding.edtNome.text.toString().isNotEmpty()

    fun interface Callback {
        fun feito(porta: Int?, ip: String)
    }

}