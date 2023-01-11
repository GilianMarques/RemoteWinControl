package gmarques.remotewincontrol.presenter.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.databinding.FragmentMainBinding
import gmarques.remotewincontrol.rede.dtos.cliente.TIPO_DTO_CLIENTE
import gmarques.remotewincontrol.presenter.Permissoes
import gmarques.remotewincontrol.presenter.Vibrador
import gmarques.remotewincontrol.presenter.mouse.scroll.ScrollClique
import gmarques.remotewincontrol.presenter.mouse.scroll.ScrollInfinito
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
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
        initFabAcoes()
        initMousePad()
        initBotoesMouse()
        observerVibracaoDeScroll()
        observerVibracaoDoMousePad()
        // TODO:     verificarPermissaoDeAcessibilidade()
        //    mostrarDialogoDeAcoes()
    }

    private fun initFabAcoes() {
        binding.fabAcoes.setOnClickListener {
            mostarBottomsheetAcoes()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initBotoesMouse() {
        binding.mouseBtnEsq.setOnClickListener { viewModel.mouseClique(TIPO_DTO_CLIENTE.MOUSE_CLICK_ESQ) }
        binding.rvInfiniteScroll.setOnTouchListener(ScrollClique())//chama o onClick qdo detecta um clique por evento de toque
        binding.rvInfiniteScroll.setOnClickListener { viewModel.mouseClique(TIPO_DTO_CLIENTE.MOUSE_CLICK_CEN) }
        binding.mouseBtnDir.setOnClickListener { viewModel.mouseClique(TIPO_DTO_CLIENTE.MOUSE_CLICK_DIR) }

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
        lifecycleScope.launch { mScrollInfinito.inicializar() }

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
                    TIPO_DTO_CLIENTE.NONE -> {}
                    TIPO_DTO_CLIENTE.PAD_MOVE -> {}
                    TIPO_DTO_CLIENTE.PAD_CLICK_TWO_FINGERS -> Vibrador.vibClickTwoFingers()
                    TIPO_DTO_CLIENTE.PAD_LONG_CLICK -> Vibrador.vibLongCLick()
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
                    R.id.acessibilidade -> abrirTelaDeAcessibilidade()
                    R.id.ip -> mostrarDialogoIpPorta()
                    R.id.acoes -> mostrarDialogoDeAcoes()
                }
                return true
            }
        }

        val menuHost = requireActivity()
        menuHost.addMenuProvider(provider)
    }

    private fun mostrarDialogoDeAcoes() {

        DialogoAcoes(this) {

        }
    }

    private fun mostrarDialogoIpPorta() {

        DialogoPortaIp(this) { porta, ip ->
            viewModel.atualizarEnderecosEnotificar(porta, ip)
        }

    }

    private fun mostarBottomsheetAcoes() {

        val modalBottomSheet = BottomSheetAcoes()
        modalBottomSheet.show(parentFragmentManager, BottomSheetAcoes::class.java.name)

    }

    private fun verificarPermissaoDeAcessibilidade() {
        if (!Permissoes().permissaoDeAcessibilidadeConcedida()) {
            val snack =
                    Snackbar.make(binding.mouseBtnMeio, getString(R.string.Permissao_de_acessibilidade_necessaria_para_controlar), Snackbar.LENGTH_INDEFINITE)

            snack.setAction(getString(R.string.Permitir)) {
                abrirTelaDeAcessibilidade()
            }

            snack.show()
        }

    }

    private fun abrirTelaDeAcessibilidade() {
        startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
    }


}