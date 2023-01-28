package gmarques.remotewincontrol.presenter.ui

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.data.PREFS_TIPO_NIGHT_MODE
import gmarques.remotewincontrol.data.Preferencias
import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import gmarques.remotewincontrol.domain.volume.VolumeHelper
import gmarques.remotewincontrol.presenter.Vibrador
import gmarques.remotewincontrol.rede.io.RedeController
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    // TODO: usar sensores para decidir quando desligar o pc

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.materialToolbar))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        VolumeHelper.inicializar(this).salvarVolumeAtual()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

            lifecycleScope.launch {
                RedeController.enviar(DtoCliente(TIPO_EVENTO_CLIENTE.VOLUME_MENOS))
            }
            Vibrador.vibInteracao()
            VolumeHelper.setarVolumeOriginal()
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {

            lifecycleScope.launch {
                RedeController.enviar(DtoCliente(TIPO_EVENTO_CLIENTE.VOLUME_MAIS))
            }
            Vibrador.vibInteracao()
            VolumeHelper.setarVolumeOriginal()
        }

        return super.onKeyDown(keyCode, event)
    }

}