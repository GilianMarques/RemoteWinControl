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

class ServicoAgendarDesligamento : Service() {

    companion object {

        val listeners = ArrayList<DesligamentoController.Listener>()
        var servicoDesligamento: ServicoAgendarDesligamento? = null
            private set
    }

    private val ID_CANAL = BuildConfig.APPLICATION_ID.plus("servico_de_desligamento")
    private val ID_NOTIFICACAO = 6935

    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private val desligamentoController = DesligamentoController(criarListener())

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        criarCanalDeNotificacao()
        startForeground(ID_NOTIFICACAO, criarNotificacao(getString(R.string.Tempo_ate_desligar)))

        val tempoEmMinutos = intent?.getIntExtra("tempoEmMinutos", 0) ?: -1
        if (tempoEmMinutos == -1) desligamentoController.cancelarAgendamento()
        else desligamentoController.agendarDesligamento(tempoEmMinutos)

        servicoDesligamento = this@ServicoAgendarDesligamento

        return START_STICKY
    }

    override fun onBind(intent: Intent): Nothing? = null

    private fun criarListener(): DesligamentoController.Listener {
        return object : DesligamentoController.Listener {

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
            override fun abortarAgendamento(segsAteDesligar: Int) {
                listeners.forEach { it.abortarAgendamento(segsAteDesligar) }

                if (segsAteDesligar <= DELAY_DESLIGAMENTO) cancelarDesligamentoNoWindows()

                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }

            /**
             * @See DesligamentoController
             * */
            override fun quaseDesligando(segsAteDesligar: Int) {
                listeners.forEach { it.quaseDesligando(segsAteDesligar) }
            }

            /**
             * @See DesligamentoController
             * */
            override fun desligar() {
                listeners.forEach { it.desligar() }
                serviceScope.launch {

                    //cancela qqer agendamento existente antes de mandar desligar, garantindo que o pc vai ser desligado imediatamente
                    cancelarDesligamentoNoWindows()
                    delay(1000)
                    configurarWindowsParaDesligar()

                    delay(2000)
                    exitProcess(0)
                }
            }
        }
    }

    fun reagendar(tempoEmMinutos: Int) {
        desligamentoController.agendarDesligamento(tempoEmMinutos)
    }

    fun abortar() {
        desligamentoController.cancelarAgendamento()
    }

    private fun cancelarDesligamentoNoWindows() {
        val dto = DtoCliente(TIPO_EVENTO_CLIENTE.ABORTAR_DESLIGAMENTO)
        serviceScope.launch { RedeController.enviar(dto) }
    }

    private fun configurarWindowsParaDesligar() {

        serviceScope.launch {
            val dto = DtoCliente(TIPO_EVENTO_CLIENTE.AGENDAR_DESLIGAMENTO)
                .addInt("segundosDeAtraso", 1)
            RedeController.enviar(dto)
        }
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

    override fun onDestroy() {
        servicoDesligamento = null
        super.onDestroy()
    }
}