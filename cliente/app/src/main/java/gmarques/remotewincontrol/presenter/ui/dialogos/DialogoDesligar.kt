package gmarques.remotewincontrol.presenter.ui.dialogos

import android.annotation.SuppressLint
import android.view.View.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.data.PREFS_MINS_ATE_DESLIGAR
import gmarques.remotewincontrol.data.Preferencias
import gmarques.remotewincontrol.databinding.DialogoDesligarBinding
import gmarques.remotewincontrol.presenter.Vibrador


class DialogoDesligar(
    private val fragmento: Fragment, private val callback: (minutosAteDesligar: Int) -> Any,
) {
    private val TEMPO_MINIMO = 0
    private val TEMPO_MAXIMO = 2880/*48hrs*/

    private var minutosAteDesligar: Int = 1
    private var binding: DialogoDesligarBinding =   DialogoDesligarBinding.inflate(fragmento.layoutInflater)

    private val regex = Regex("[^0-9]")

    private var dialog: BottomSheetDialog = BottomSheetDialog(fragmento.requireContext())

    init {

        dialog.setContentView(binding.root)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.show()

        initToolbar()
        initEditTextMinutos()
        initBotoesViewMinutos()
        initBotaoSalvar()
    }

    private fun initBotaoSalvar() {
        binding.btnAgendar.setOnClickListener {
            callback(minutosAteDesligar)
            dialog.dismiss()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initBotoesViewMinutos() {

        binding.ivAdd.setOnClickListener {
            regex.replace(binding.edtMinutos.text, "")
                .toIntOrNull()
                .let { atualizarMostrador((it ?: 0) + 5) }
        }

        binding.ivAdd.setOnLongClickListener {
            atualizarMostrador(TEMPO_MAXIMO)
            true
        }

        binding.ivSub.setOnClickListener {
            regex.replace(binding.edtMinutos.text, "")
                .toIntOrNull()
                .let { atualizarMostrador((it ?: 0) - 5) }
        }

        binding.ivSub.setOnLongClickListener {
            atualizarMostrador(TEMPO_MINIMO)
            true
        }

        minutosAteDesligar = Preferencias().getInt(PREFS_MINS_ATE_DESLIGAR, 1)

        atualizarMostrador(minutosAteDesligar)

    }

    private fun initEditTextMinutos() {


        binding.edtMinutos.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                atualizarMostrador(regex.replace(binding.edtMinutos.text, "").toInt())
                view.clearFocus()
            }
            false
        }
    }

    private fun atualizarMostrador(minutos: Int) {
        val tempoVerificado = minutos.coerceIn(TEMPO_MINIMO, TEMPO_MAXIMO)
        minutosAteDesligar = tempoVerificado
        Preferencias().putInt(PREFS_MINS_ATE_DESLIGAR, minutosAteDesligar)
        binding.edtMinutos.setText(
            String.format(
                fragmento.getString(R.string.x_mins),
                minutosAteDesligar
            )
        )
        Vibrador.vibInteracao()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { dialog.dismiss() }
    }

}