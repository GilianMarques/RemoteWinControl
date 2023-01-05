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
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.databinding.FragmentMainBinding
import gmarques.remotewincontrol.domain.GestureType
import gmarques.remotewincontrol.presenter.Permissoes
import gmarques.remotewincontrol.presenter.Vibrador
import gmarques.remotewincontrol.presenter.VolumeHelper
import gmarques.remotewincontrol.presenter.mouse.ScrollInfinito
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

        VolumeHelper.inicializar(requireContext()).salvarVolumeAtual()
        initMenu()
        initScroll()
        initMousePad()
        initBotoesMouse()
        observerVibracaoDeScroll()
        observerVibracaoDoMousePad()
    }

    private fun initBotoesMouse() {
        binding.mouseBtnEsq.setOnClickListener { viewModel.mouseClique(1) }
        binding.rvInfiniteScroll.setOnClickListener { viewModel.mouseClique(3) }
        binding.mouseBtnDir.setOnClickListener { viewModel.mouseClique(2) }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initMousePad() {
        binding.mousePad.setOnTouchListener(viewModel.getMousePadHandler())
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
                    GestureType.CLICK -> Vibrador.vibCLick()
                    GestureType.CLICK_TWO_FINGERS -> Vibrador.vibClickTwoFingers()
                    GestureType.LONG_CLICK -> Vibrador.vibLongCLick()
                    else -> {}
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
                    /*R.id.sensibilidade-> mostrarDialogoDeSensibilidades()*/
                }
                return true
            }
        }

        val menuHost = requireActivity()
        menuHost.addMenuProvider(provider)
    }

    private fun abrirTelaDeAcessibilidade() {
        if (!Permissoes().permissaoDeAcessibilidadeConcedida(requireContext())) {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
    }

}