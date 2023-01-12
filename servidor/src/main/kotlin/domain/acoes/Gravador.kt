package domain.acoes

import domain.acoes.Etapa.TIPO
import domain.listeners.KeyboardListener
import domain.listeners.MouseListener

class Gravador : KeyboardListener.Callback, MouseListener.MouseCallback {

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
        acao.etapas.add(
            Etapa(tempoDecorrido(), TIPO.TECLADO_PRESS)
                .also {
                    it.botao = rawCode
                    it.descricao = "Teclado Press tecla $nomeTecla"
                }
        )
    }

    override fun tecladoSoltouTecla(rawCode: Int, nomeTecla: String) {

        acao.etapas.add(
            Etapa(tempoDecorrido(), TIPO.TECLADO_SOLTAR)
                .also {
                    it.botao = rawCode
                    it.descricao = "Teclado soltar tecla $nomeTecla"
                }
        )

    }

    override fun mouseBotaoPressionado(botao: Int) {
        acao.etapas.add(
            Etapa(tempoDecorrido(), TIPO.MOUSE_PRESS)
                .also {
                    it.botao = botao
                    it.descricao = "mouse press tecla $botao"
                }
        )
    }

    override fun mouseBotaoSolto(botao: Int) {
        acao.etapas.add(
            Etapa(tempoDecorrido(), TIPO.MOUSE_SOLTAR)
                .also {
                    it.botao = botao
                    it.descricao = "mouse solta tecla $botao"
                }
        )
    }

    override fun mouseMoveu(x: Int, y: Int) {

        acao.etapas.add(
            Etapa(tempoDecorrido(), TIPO.MOUSE_MOVER)
                .also {

                    it.movX = x
                    it.movY = y
                    it.descricao = "mouse movido x: $x  y: $y "
                }
        )

    }

    override fun mouseRolou(direcao: Int) {
        acao.etapas.add(
            Etapa(tempoDecorrido(), TIPO.MOUSE_ROLAR)
                .also {
                    it.rolarDirecao = direcao
                    it.descricao = "mouse rolado dir:$direcao"
                }
        )
    }


}

