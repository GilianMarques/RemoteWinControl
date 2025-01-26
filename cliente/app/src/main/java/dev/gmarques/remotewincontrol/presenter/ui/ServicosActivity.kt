package dev.gmarques.remotewincontrol.presenter.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dev.gmarques.remotewincontrol.R
import dev.gmarques.remotewincontrol.domain.funcoes.desligamento.ABORTAR_AGEND_DESLIGAR
import dev.gmarques.remotewincontrol.domain.funcoes.desligamento.ServicoAgendarDesligamento

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