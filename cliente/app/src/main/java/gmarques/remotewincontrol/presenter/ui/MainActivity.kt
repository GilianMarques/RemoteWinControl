package gmarques.remotewincontrol.presenter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.domain.Servidor
import gmarques.remotewincontrol.rede.Cliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.materialToolbar))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }


        CoroutineScope(Job()).launch {
            println("ligando servidor")
            Servidor.ligar()
        }

        CoroutineScope(Job()).launch {
            println("ligando cliente")
            Cliente.enviarMsg("Teste de mensagem")

        }

    }

}