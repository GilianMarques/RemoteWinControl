package domain.acoes

import domain.acoes.Etapa.TIPO
import domain.listeners.KeyboardListener
import domain.listeners.MouseListener
import java.awt.MouseInfo

class Gravador(val ignorarMovimentoMouse: Boolean) : KeyboardListener.Callback, MouseListener.MouseCallback {

    private val tecladoListener = KeyboardListener()
    private val mouseListener = MouseListener()
    private val horarioInicio = System.currentTimeMillis()

    private lateinit var acao: Acao

    fun gravar() {
        acao = Acao()
        tecladoListener.ligar(this)
        mouseListener.ligar(this)

    }

    fun pararGravacao(): Acao {
        tecladoListener.desligar()
        mouseListener.desligar()
        return acao
    }

    private fun tempoDecorrido() = (System.currentTimeMillis() - horarioInicio)

    override fun tecladoPressionouTecla(rawCode: Int, nomeTecla: String) {
        acao.etapas.add(Etapa(tempoDecorrido(), TIPO.TECLADO_PRESS).also {
                it.botao = rawCode
                it.descricao = "Teclado Press. tecla $nomeTecla"
            })
    }

    override fun tecladoSoltouTecla(rawCode: Int, nomeTecla: String) {

        acao.etapas.add(Etapa(tempoDecorrido(), TIPO.TECLADO_SOLTAR).also {
                it.botao = rawCode
                it.descricao = "Teclado soltar tecla $nomeTecla"
            })

    }

    override fun mouseBotaoPressionado(botao: Int) {
        acao.etapas.add(Etapa(tempoDecorrido(), TIPO.MOUSE_PRESS).also {
                it.botao = botao
                it.coordY = MouseInfo.getPointerInfo().location.x;
                it.coordX = MouseInfo.getPointerInfo().location.y;
                it.descricao = "Mouse press. tecla $botao"
            })
    }

    override fun mouseBotaoSolto(botao: Int) {
        acao.etapas.add(Etapa(tempoDecorrido(), TIPO.MOUSE_SOLTAR).also {
                it.botao = botao
                it.coordY = MouseInfo.getPointerInfo().location.x;
                it.coordX = MouseInfo.getPointerInfo().location.y;
                it.descricao = "Mouse solta tecla $botao"
            })
    }

    override fun mouseMoveu(x: Int, y: Int, arrastando: Boolean) {
        if (!arrastando && ignorarMovimentoMouse) return

        acao.etapas.add(Etapa(tempoDecorrido(), TIPO.MOUSE_MOVER).also {

                it.coordX = x
                it.coordY = y
                it.descricao = "Mouse mover x: $x  y: $y "
            })

    }

    override fun mouseRolou(direcao: Int) {
        acao.etapas.add(Etapa(tempoDecorrido(), TIPO.MOUSE_ROLAR).also {
                it.rolarDirecao = direcao
                it.descricao = "Mouse rolar dir: $direcao"
            })
    }


}

