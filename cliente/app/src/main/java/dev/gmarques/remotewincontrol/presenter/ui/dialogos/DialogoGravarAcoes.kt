package dev.gmarques.remotewincontrol.presenter.ui.dialogos

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dev.gmarques.remotewincontrol.databinding.DialogoGravarAcoesBinding
import dev.gmarques.remotewincontrol.domain.funcoes.acoes.AcaoController
import dev.gmarques.remotewincontrol.presenter.Vibrador
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class DialogoGravarAcoes(fragmento: Fragment, private val callback: Callback) {

    private var binding = DialogoGravarAcoesBinding.inflate(fragmento.layoutInflater)
    private var dialog: BottomSheetDialog
    private var acaoController: AcaoController
    private val scope: CoroutineScope
    private var gravando = false

    init {
        scope = fragmento.lifecycleScope
        initViews()
        dialog = BottomSheetDialog(fragmento.requireContext())
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.show()
        dialog.setOnDismissListener { callback.feito() }
        acaoController = AcaoController(::acaoRecebida)
    }

    private fun initViews() {

        binding.btnGravar.setOnClickListener { iniciarGravacao() }

        binding.btnPararGravacao.setOnClickListener { pararGravacao() }

        binding.btnSalvar.setOnClickListener {
            if (nomeValido()) {
                acaoController.salvarAcao(binding.edtNome.text.toString())
                dialog.dismiss()
            } else Vibrador.vibErro()
        }

        binding.btnCancelar.setOnClickListener {
            cancelarGravacao()
        }
        binding.toolbar.setNavigationOnClickListener {
            cancelarGravacao()
        }

    }

    private fun cancelarGravacao() = scope.launch(IO) {

        if (gravando) {
            if (acaoController.abortarGravacao()) {
                dialog.dismiss()
            }
        } else dialog.dismiss()
    }

    private fun pararGravacao() = scope.launch {
        if (acaoController.pararGravacao()) {
            binding.btnPararGravacao.visibility = View.GONE
            binding.inputNome.visibility = View.VISIBLE
            binding.inputNome.requestFocus()
            gravando = false

        }
    }

    private fun iniciarGravacao() = scope.launch {
        if (acaoController.iniciarGravacao(binding.sMovMouse.isChecked)) {
            binding.btnPararGravacao.visibility = View.VISIBLE
            binding.btnGravar.visibility = View.GONE
            binding.sMovMouse.visibility = View.GONE
            gravando = true
        }
    }

    /**
     * invocado a partir da classe Acoes para avisar que o script gravado no pc foi recebido
     * @see AcaoController
     * */
    private fun acaoRecebida() = scope.launch(Dispatchers.Main) {
        binding.btnSalvar.isEnabled = true
        binding.btnSalvar.isClickable = true
    }

    private fun nomeValido() = binding.edtNome.text.toString().isNotEmpty()

    fun interface Callback {
        fun feito()
    }


}