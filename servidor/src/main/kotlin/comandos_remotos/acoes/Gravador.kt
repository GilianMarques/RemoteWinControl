package comandos_remotos.acoes

import perifericos.entrada.KeyboardListener
import perifericos.entrada.MouseListener

class Gravador : KeyboardListener.Callback, MouseListener.MouseCallback {

    private val tecladoListener = KeyboardListener()
    private val mouseListener = MouseListener()
    private val horarioInicio = System.currentTimeMillis()

    /**consiste do tempo em que a acao foi feita e o comando que sera usado no nirCmd*/
    private val etapas = ArrayList<Etapa>()


    fun gravar() {
        tecladoListener.ligar(this)
        mouseListener.ligar(this)

    }

    fun pararGravacao(): ArrayList<Etapa> {
        tecladoListener.desligar()
        mouseListener.desligar()
        return etapas
    }

    private fun tempoDecorrido() = (System.currentTimeMillis() - horarioInicio)

    override fun tecladoPressionouTecla(rawCode: Int, nomeTecla: String) {
        etapas.add(Etapa(tempoDecorrido(), AcoesDoUsuario.TECLADO_PRESS)
            .also {
                it.botao = rawCode
                it.descricao = "Teclado Press tecla $nomeTecla"
            }
        )
    }

    override fun tecladoSoltouTecla(rawCode: Int, nomeTecla: String) {

        etapas.add(Etapa(tempoDecorrido(), AcoesDoUsuario.TECLADO_SOLTAR)
            .also {
                it.botao = rawCode
                it.descricao = "Teclado soltar tecla $nomeTecla"
            }
        )

    }

    override fun mouseBotaoPressionado(botao: Int) {
        etapas.add(Etapa(tempoDecorrido(), AcoesDoUsuario.MOUSE_PRESS)
            .also {
                it.botao = botao
                it.descricao = "mouse press tecla $botao"
            }
        )
    }

    override fun mouseBotaoSolto(botao: Int) {
        etapas.add(Etapa(tempoDecorrido(), AcoesDoUsuario.MOUSE_SOLTAR)
            .also {
                it.botao = botao
                it.descricao = "mouse solta tecla $botao"
            }
        )
    }

    override fun mouseMoveu(x: Int, y: Int) {

        etapas.add(Etapa(tempoDecorrido(), AcoesDoUsuario.MOUSE_MOVER)
            .also {

                it.movX = x
                it.movY = y
                it.descricao = "mouse movido x: $x  y: $y "
            }
        )

    }

    override fun mouseRolou(direcao: Int) {
        etapas.add(Etapa(tempoDecorrido(), AcoesDoUsuario.MOUSE_ROLAR)
            .also {
                it.rolarDirecao = direcao
                it.descricao = "mouse rolado dir:$direcao"
            }
        )
    }


}

