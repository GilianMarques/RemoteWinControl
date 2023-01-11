package gmarques.remotewincontrol.presenter.ui

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import gmarques.remotewincontrol.databinding.DialogoIpPortaBinding
import gmarques.remotewincontrol.rede.EnderecosDeRede

class DialogoPortaIp(fragmento: Fragment, private val callback: Callback) {

    private var binding = DialogoIpPortaBinding.inflate(fragmento.layoutInflater)
    private var dialog: BottomSheetDialog

    init {
        initViews()
        dialog = BottomSheetDialog(fragmento.requireContext())
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.show()
    }

    private fun initViews() {
        binding.edtPorta.setText("${EnderecosDeRede.portaDoServidor}")
        binding.edtIp.setText(EnderecosDeRede.ipDoServidor)

        binding.btnSalvar.setOnClickListener {


            callback.feito(
                binding.edtPorta.text.toString().toIntOrNull(),
                binding.edtIp.text.toString())

            dialog.dismiss()
        }

        binding.btnCancelar.setOnClickListener {
            dialog.dismiss()
        }
        binding.toolbar.setNavigationOnClickListener {
            dialog.dismiss()
        }

    }

    fun interface Callback {
        fun feito(porta: Int?, ip: String)
    }

}