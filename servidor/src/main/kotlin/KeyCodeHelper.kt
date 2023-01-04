import java.awt.event.KeyEvent

/**
 * Printa na tela o rawCode de todas as teclas */
fun main() {
    for (rawCode in 0..999999) {
        val text = KeyEvent.getKeyText(rawCode)

        if (!text.contains("Unknown rawCode: ")) {
            println("rawCode: $rawCode text: $text ")
        }
    }

}
