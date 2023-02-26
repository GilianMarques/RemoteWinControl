package gmarques.remotewincontrol.presenter.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.domain.ABORTAR_AGEND_DESLIGAR
import gmarques.remotewincontrol.domain.desligamento_agendado.ServicoAgendarDesligamento

class ServicosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicos)
        verificarActionDaIntent()
    }


    private fun verificarActionDaIntent() {
        Log.d("USUK", "ServicosActivity.verificarActionDaIntent: ")
        // intent criada pelo serviço ServicoAgendarDesligamento
        if (intent.action == ABORTAR_AGEND_DESLIGAR) {
            ServicoAgendarDesligamento.servicoDesligamento!!.abortar()
            finish()
        }
    }
}