package gmarques.remotewincontrol.presenter.ui

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.presenter.volume.VolumeHandler

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


    }

}