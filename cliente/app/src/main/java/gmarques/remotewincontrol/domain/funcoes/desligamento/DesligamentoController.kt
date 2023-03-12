package gmarques.remotewincontrol.domain.funcoes.desligamento

import java.util.*

/**
 * Aqui fica toda a logica do timer de desligar o PC. Depois de inicializar o timer, os listeners
 * registrados vao receber eventos a cada etapa do processo até que o timer chegue a 0 ou seja cancelado.
 */
class DesligamentoController(val listener: Listener) {

    private var segsAteDesligar: Int = -123
    private var timerDeslPc: Timer? = null

    fun agendarDesligamento(minutosAteDesligar: Int) {
        if (minutosAteDesligar == 0) avisarDesligarComputador()
        else iniciarTimer(minutosAteDesligar)
    }

    fun cancelarAgendamento() {
        avisarAbortarAgendamento()
    }

    private fun iniciarTimer(minutosAteDesligar: Int) {

        segsAteDesligar = minutosAteDesligar * 60

        timerDeslPc?.cancel()
        timerDeslPc = Timer()
        timerDeslPc!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                --segsAteDesligar
                atualizarListenersTimer()
                executarLogicaDoTimer()
            }

            override fun cancel(): Boolean {
                timerDeslPc = null
                return super.cancel()
            }

        }, 0, 1000)
    }

    private fun executarLogicaDoTimer() {
        if (segsAteDesligar == DELAY_DESLIGAMENTO) {
            avisarQuaseDesligando()
        } else if (segsAteDesligar == 0) {
            avisarDesligarComputador()
        } else if (segsAteDesligar < 0) {
            throw java.lang.IllegalStateException("O timer deveria parar no 0")
        }
    }

    private fun atualizarListenersTimer() {
        listener.tick(formatarTimer(segsAteDesligar))
    }

    private fun formatarTimer(segs: Int): String {

        val segundos = segs % 60
        val minutos = segs / 60 % 60
        val horas = segs / 3600

        return if (horas > 0) "%02d:%02d:%02d".format(horas, minutos, segundos)
        else "%02d:%02d".format(minutos, segundos)
    }

    private fun avisarQuaseDesligando() {
        listener.quaseDesligando(segsAteDesligar)
    }

    private fun avisarDesligarComputador() {
        timerDeslPc?.cancel()
        listener.desligar()
    }

    private fun avisarAbortarAgendamento() {
        timerDeslPc?.cancel()
        listener.abortarAgendamento(segsAteDesligar)
    }

    interface Listener {
        fun tick(tempoFormatado: String)
        fun abortarAgendamento(segsAteDesligar: Int)
        fun quaseDesligando(segsAteDesligar: Int)
        fun desligar()
    }

}