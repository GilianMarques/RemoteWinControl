package domain.acoes.reprodutores

class NirCmdTeclas {
    companion object {

        /**
         * O codigo da tecla 'Backspace' é '8' porem o NirCmd digita '8' ao inves de 'Backspace'
         * para evitar esse (e outros) conflito(s) essafunção faz a troca do codigo por um nome valido
         * aceito pelo 'NirCmd' de acordo com  {https://nircmd.nirsoft.net/sendkey.html}
         * */
        fun codigoTecla(botao: Int): String = when (botao) {
            8 -> "backspace"
            else -> "$botao"
        }
    }

}
