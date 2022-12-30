package perifericos.saida

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent

class KeyEventDispatcher {
    /**
     * Simula um clique de uma tecla seja digitavel (a,b,c,@,+) ou nao (F1,Alt,Del)
     */
    fun dispatch() {
        GlobalScreen.postNativeEvent(
            NativeKeyEvent(
                2401,
                0,
                112,
                34,
                NativeKeyEvent.CHAR_UNDEFINED
            )
        )
    }

}