package gmarques.remotewincontrol.domain.desligamento_agendado

import gmarques.remotewincontrol.domain.DELAY_DESLIGAMENTO
import java.util.*

class DesligamentoController(val listener: Listener) {

    private var millisAteDesligar: Long = -123
    private var timerDeslPc: Timer? = null

    fun agendarDesligamento(minutosAteDesligar: Int) {
        when (minutosAteDesligar) {
            0 -> desligar()
            -1 -> abortarAgendamento()
            else -> iniciarTimer(minutosAteDesligar)
        }
    }

    private fun iniciarTimer(minutosAteDesligar: Int) {

        if (timerDeslPc != null) abortarAgendamento()

        millisAteDesligar = minutosAteDesligar * 60 * 1000L

        timerDeslPc = Timer()
        timerDeslPc!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {

                millisAteDesligar -= 1000

                listener.tick(formatarTimer(millisAteDesligar))

                if (millisAteDesligar == DELAY_DESLIGAMENTO) listener.quaseDesligando(millisAteDesligar)
                else if (millisAteDesligar == 0L) desligar()

            }

            override fun cancel(): Boolean {
                timerDeslPc = null
                return super.cancel()
            }

        }, 0, 1000)
    }

    private fun formatarTimer(millis: Long): String {

        val totalSegundos = millis / 1000
        val segundos = totalSegundos % 60
        val minutos = totalSegundos / 60 % 60
        val horas = totalSegundos / 3600

        return if (horas > 0) "%02d:%02d:%02d".format(horas, minutos, segundos)
        else "%02d:%02d".format(minutos, segundos)
    }

    private fun desligar() {
        timerDeslPc?.cancel()
        listener.desligar()
    }

    private fun abortarAgendamento() {
        timerDeslPc?.cancel()
        listener.abortarAgendamento(millisAteDesligar)
    }

    interface Listener {
        fun tick(tempoFormatado: String)
        fun abortarAgendamento(millisAteDesligar: Long)
        fun quaseDesligando(millisAteDesligar: Long)
        fun desligar()
    }

}