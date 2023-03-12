package gmarques.remotewincontrol.presenter.ui.bs_ver_acoes

import android.annotation.SuppressLint
import android.view.View.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.data.AcoesDao
import gmarques.remotewincontrol.databinding.DialogoEditarAcaoBinding
import gmarques.remotewincontrol.domain.funcoes.acoes.Acao
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class DialogoEditarAcao(
    private val acao: Acao,
    private val fragmento: Fragment,
    private val callback: () -> Unit,
) {

    private var acoesDao = AcoesDao()
    private var binding: DialogoEditarAcaoBinding =
            DialogoEditarAcaoBinding.inflate(fragmento.layoutInflater)


    private var dialog: androidx.appcompat.app.AlertDialog =
            MaterialAlertDialogBuilder(fragmento.requireContext()).create()

    init {

        dialog.setView(binding.root)
        dialog.show()

        initToolbar()
        initEdtVelocidade()
        initEdtNome()
        initBtnRemover()
        initBtnConfirmarRemocao()
        initBtnCancelar()
        initBtnSalvar()
    }

    private fun initBtnSalvar() {
        binding.btnSalvar.setOnClickListener {

            aplicarVelocidade()
            aplicarNome()

            acoesDao.salvarAcao(acao)
            dialog.dismiss()
            callback.invoke()

        }

    }

    private fun initBtnCancelar() {
        binding.btnCancelar.setOnClickListener { dialog.dismiss() }
    }

    private fun initBtnConfirmarRemocao() {
        binding.btnConfirmarRemocao.setOnClickListener {
            acoesDao.removerAcao(acao.id)
            dialog.dismiss()

        }

    }

    private fun initBtnRemover() {
        binding.btnRemover.setOnClickListener {

            binding.btnConfirmarRemocao.visibility = VISIBLE
            it.visibility = GONE

            fragmento.lifecycleScope.launch {
                delay(3000)
                if (dialog.isShowing) {
                    binding.btnConfirmarRemocao.visibility = GONE
                    it.visibility = VISIBLE
                }
            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.title = String.format(fragmento.getString(R.string.Editar_x), acao.nome)
        binding.toolbar.setNavigationOnClickListener { dialog.dismiss() }
    }

    @SuppressLint("SetTextI18n")
    private fun initEdtVelocidade() {
        binding.edtVelocidade.setText("${acao.velocidade}x")
        binding.edtVelocidade.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) (v as EditText).setText(
                "${binding.edtVelocidade.text}x"
                    .replace(".0x", "x")
            )
        }
    }

    private fun initEdtNome() {
        binding.edtNome.setText(acao.nome)
    }

    /**
     * Trata e inseri no objeto acao o valor referente a velocidade que o usuario inseriu na interface
     */
    private fun aplicarVelocidade() {
        Regex("""[^\d.]""")
            .replace(binding.edtVelocidade.text.toString(), "")
            .ifEmpty { "1" }
            .apply {
                acao.velocidade = toFloat()
                    .coerceIn(Acao.velocidadeMinima, Acao.velocidadeMaxima)
            }

    }

    /**
     * Trata e inseri no objeto acao o valor referente ao nome que o usuario inseriu na interface
     */
    private fun aplicarNome() {

        val nome = binding.edtNome.text
            .toString()
            .ifEmpty {
                return
            }

        acao.nome = nome

    }
}