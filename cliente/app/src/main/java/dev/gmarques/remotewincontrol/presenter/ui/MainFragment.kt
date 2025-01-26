package dev.gmarques.remotewincontrol.presenter.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.gmarques.remotewincontrol.App
import dev.gmarques.remotewincontrol.R
import dev.gmarques.remotewincontrol.data.PREFS_TIPO_NIGHT_MODE
import dev.gmarques.remotewincontrol.data.Preferencias
import dev.gmarques.remotewincontrol.databinding.FragmentMainBinding
import dev.gmarques.remotewincontrol.domain.funcoes.desligamento.DesligamentoController
import dev.gmarques.remotewincontrol.domain.funcoes.desligamento.ServicoAgendarDesligamento
import dev.gmarques.remotewincontrol.domain.funcoes.desligamento.ServicoAgendarDesligamento.Companion.servicoDesligamento
import dev.gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import dev.gmarques.remotewincontrol.domain.funcoes.mouse.scroll.ScrollClique
import dev.gmarques.remotewincontrol.domain.funcoes.mouse.scroll.ScrollInfinito
import dev.gmarques.remotewincontrol.presenter.Vibrador
import dev.gmarques.remotewincontrol.presenter.ui.bs_ver_acoes.BottomSheetVerAcoes
import dev.gmarques.remotewincontrol.presenter.ui.dialogos.DialogoDesligar
import dev.gmarques.remotewincontrol.presenter.ui.dialogos.DialogoGravarAcoes
import dev.gmarques.remotewincontrol.presenter.ui.dialogos.DialogoPortaIp
import dev.gmarques.remotewincontrol.domain.network.io.RedeController

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var bottomSheet: BottomSheetVerAcoes
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val listener = object : DesligamentoController.Listener {

        override fun tick(tempoFormatado: String) {

            lifecycleScope.launch {
                binding.tvTimer.visibility = VISIBLE
                binding.tvTimer.text = tempoFormatado
            }

        }

        override fun abortarAgendamento(segsAteDesligar: Int) {
            lifecycleScope.launch {
                binding.tvTimer.visibility = GONE
            }
        }

        override fun quaseDesligando(segsAteDesligar: Int) {
            Vibrador.vibDesligar()
        }

        override fun desligar() {
            requireActivity().finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()
        initScroll()
        initFabs()
        initMousePad()
        initBotoesMouse()
        observerVibracaoDeScroll()
        observerVibracaoDoMousePad()
        initBottomSheet()
        observarPing()
        addListenersAoServicoDeDesligamento()

    }

    private fun initFabs() {
        binding.fabAcoes.setOnClickListener {
            bottomSheet.exibir()
        }

        binding.fabVoz.setOnClickListener {
            // TODO:  bottomSheet.exibir()
        }

    }

    private fun initBottomSheet() {

        bottomSheet = BottomSheetVerAcoes(this@MainFragment, binding) {
            mostrarDialogoGravarAcoes()
        }


    }

    private fun observarPing() {
        RedeController.ping.observe(viewLifecycleOwner) {
            binding.tvPing.text = String.format(getString(R.string.Ping_x), it.toString())
        }
    }

    override fun onDetach() {
        removerListenersAoServicoDeDesligamento()
        super.onDetach()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initBotoesMouse() {
        binding.mouseBtnEsq.setOnClickListener { viewModel.mouseClique(TIPO_EVENTO_CLIENTE.MOUSE_CLICK_ESQ) }
        binding.rvInfiniteScroll.setOnTouchListener(ScrollClique())//chama o onClick qdo detecta um clique por evento de toque
        binding.rvInfiniteScroll.setOnClickListener { viewModel.mouseClique(TIPO_EVENTO_CLIENTE.MOUSE_CLICK_CEN) }
        binding.mouseBtnDir.setOnClickListener { viewModel.mouseClique(TIPO_EVENTO_CLIENTE.MOUSE_CLICK_DIR) }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initMousePad() {
        binding.mousePad.setOnTouchListener(viewModel.getMousePadListener())
    }

    private fun initScroll() {

        val scrollAdapter = ScrollAdapter(layoutInflater)
        val layoutManager = LinearLayoutManager(requireContext())

        val mScrollInfinito = ScrollInfinito(layoutManager, scrollAdapter)
        mScrollInfinito.scrollListener = viewModel::infiniteScrollListener
        lifecycleScope.launch {
            mScrollInfinito.inicializar()
            delay(100)
            binding.rvInfiniteScroll.visibility = VISIBLE
        }

        binding.rvInfiniteScroll.layoutManager = layoutManager
        binding.rvInfiniteScroll.adapter = scrollAdapter
        binding.rvInfiniteScroll.addOnScrollListener(mScrollInfinito)
    }

    private fun observerVibracaoDeScroll() =
        viewModel.vibrarScroll.observe(viewLifecycleOwner) { duracao ->
            if (duracao > 0) Vibrador.vibScroll(duracao, lifecycleScope)
        }

    private fun observerVibracaoDoMousePad() =
        viewModel.vibrarMousePad.observe(viewLifecycleOwner) { tipo ->
            when (tipo) {
                TIPO_EVENTO_CLIENTE.NONE -> {}
                TIPO_EVENTO_CLIENTE.MOUSE_MOVE -> {}
                TIPO_EVENTO_CLIENTE.PAD_CLICK_TWO_FINGERS -> Vibrador.vibClickTwoFingers()
                TIPO_EVENTO_CLIENTE.PAD_LONG_CLICK -> Vibrador.vibLongCLick()
                else -> Vibrador.vibCLick()

            }
        }

    private fun initMenu() {

        val provider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_principal, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.ip -> mostrarDialogoIpPorta()
                    R.id.desligar -> mostrarDialogoDesligar()
                    R.id.tema_claro -> alternarModoNoite(MODE_NIGHT_NO)
                    R.id.tema_escuro -> alternarModoNoite(MODE_NIGHT_YES)
                    R.id.tema_sistema -> alternarModoNoite(MODE_NIGHT_FOLLOW_SYSTEM)
                }
                return true
            }
        }

        val menuHost = requireActivity()
        menuHost.addMenuProvider(provider)
    }

    private fun alternarModoNoite(tipoTema: Int) {
        setDefaultNightMode(tipoTema)
        Preferencias().putInt(PREFS_TIPO_NIGHT_MODE, tipoTema)
        requireActivity().recreate()
    }

    private fun mostrarDialogoDesligar() = DialogoDesligar(this, ::agendarDesligamento)

    private fun agendarDesligamento(tempoEmMinutos: Int) {

        if (servicoDesligamento == null) {
            val servicoDesligamentoIntent = Intent(App.get, ServicoAgendarDesligamento::class.java)
            servicoDesligamentoIntent.putExtra("tempoEmMinutos", tempoEmMinutos)
            requireActivity().startService(servicoDesligamentoIntent)

        } else servicoDesligamento?.reagendar(tempoEmMinutos)
    }

    private fun addListenersAoServicoDeDesligamento() {
        ServicoAgendarDesligamento.listeners.add(listener)
    }

    private fun removerListenersAoServicoDeDesligamento() {
        ServicoAgendarDesligamento.listeners.remove(listener)
    }

    private fun mostrarDialogoGravarAcoes() {

        DialogoGravarAcoes(this) {
            bottomSheet.atualizar()
            bottomSheet.exibir()
        }
    }

    private fun mostrarDialogoIpPorta() {

        DialogoPortaIp(this) { porta, ip ->
            viewModel.atualizarEnderecosEnotificar(porta, ip)
        }

    }


}