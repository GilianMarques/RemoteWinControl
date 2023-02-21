package gmarques.remotewincontrol.domain.desligamento_agendado

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import gmarques.remotewincontrol.App
import gmarques.remotewincontrol.BuildConfig
import gmarques.remotewincontrol.R
import gmarques.remotewincontrol.domain.ABORTAR_AGEND_DESLIGAR
import gmarques.remotewincontrol.domain.DELAY_DESLIGAMENTO
import gmarques.remotewincontrol.domain.dtos.cliente.DtoCliente
import gmarques.remotewincontrol.domain.dtos.cliente.TIPO_EVENTO_CLIENTE
import gmarques.remotewincontrol.presenter.ui.MainActivity
import gmarques.remotewincontrol.presenter.ui.ServicosActivity
import gmarques.remotewincontrol.rede.io.RedeController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class ServicoAgendarDesligamento : Service(), DesligamentoController.Listener {

    companion object {

        val listeners = ArrayList<DesligamentoController.Listener>()
        var servicoDesligamento: ServicoAgendarDesligamento? = null
            private set

    }

    private val ID_CANAL = BuildConfig.APPLICATION_ID.plus("servico_de_desligamento")
    private val ID_NOTIFICACAO = 6935

    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private val desligamentoController = DesligamentoController(this)


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        criarCanalDeNotificacao()
        startForeground(ID_NOTIFICACAO, criarNotificacao(getString(R.string.Tempo_ate_desligar)))

        val tempoEmMinutos = intent?.getIntExtra("tempoEmMinutos", -1) ?: -1
        desligamentoController.agendarDesligamento(tempoEmMinutos)

        servicoDesligamento = this@ServicoAgendarDesligamento

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): Nothing? = null

    fun reagendar(tempoEmMinutos: Int) {
        desligamentoController.agendarDesligamento(tempoEmMinutos)
    }

    fun abortar() {
        desligamentoController.agendarDesligamento(-1)
    }

    private fun criarNotificacao(mensagem: String): Notification {


        return NotificationCompat.Builder(this, ID_CANAL)
            .setContentTitle(getString(R.string.Tempo_ate_desligar))
            .setContentText(mensagem)
            .setSmallIcon(R.drawable.vec_desligar)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(criarCliqueIntent())
            .addAction(R.drawable.vec_cancelar, getString(R.string.Abortar), criarAbortarIntent())
            .setOngoing(true)
            .build()

    }

    private fun criarCliqueIntent(): PendingIntent {

        val intent = Intent(this, MainActivity::class.java).apply {
            this.action = Intent.ACTION_MAIN;
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.addCategory(Intent.CATEGORY_LAUNCHER)
        }

        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun criarAbortarIntent(): PendingIntent {

        val intent = Intent(this, ServicosActivity::class.java).apply {
            this.action = ABORTAR_AGEND_DESLIGAR;
            this.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        }

        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun criarCanalDeNotificacao() {

        val canalNotificacao = NotificationChannel(
            ID_CANAL,
            getString(R.string.Servico_de_desligamento_do_pc),
            NotificationManager.IMPORTANCE_LOW
        )


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(canalNotificacao)

    }

    private fun notificar(notificacao: Notification) {
        with(NotificationManagerCompat.from(App.get)) {

            if (ActivityCompat.checkSelfPermission(
                    this@ServicoAgendarDesligamento,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(ID_NOTIFICACAO, notificacao)
            }

        }
    }

    /**
     * @See DesligamentoController
     * */
    override fun tick(tempoFormatado: String) {
        listeners.forEach { it.tick(tempoFormatado) }
        notificar(criarNotificacao(tempoFormatado))
    }

    /**
     * @See DesligamentoController
     * */
    override fun abortarAgendamento(millisAteDesligar: Long) {
        listeners.forEach { it.abortarAgendamento(millisAteDesligar) }

        if (millisAteDesligar <= DELAY_DESLIGAMENTO) {
            val dto = DtoCliente(TIPO_EVENTO_CLIENTE.ABORTAR_DESLIGAMENTO)
            serviceScope.launch { RedeController.enviar(dto) }
        }

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    /**
     * @See DesligamentoController
     * */
    override fun quaseDesligando(millisAteDesligar: Long) {
        listeners.forEach { it.quaseDesligando(millisAteDesligar) }
        serviceScope.launch {
            val dto = DtoCliente(TIPO_EVENTO_CLIENTE.AGENDAR_DESLIGAMENTO)
                .addInt("segundosDeAtraso", (millisAteDesligar / 1000).toInt())
            RedeController.enviar(dto)
        }
    }

    /**
     * @See DesligamentoController
     * */
    override fun desligar() {
        listeners.forEach { it.desligar() }
        serviceScope.launch {
            delay(2000)
            exitProcess(0)
        }
    }

    override fun onDestroy() {
        servicoDesligamento = null
        super.onDestroy()
    }
}