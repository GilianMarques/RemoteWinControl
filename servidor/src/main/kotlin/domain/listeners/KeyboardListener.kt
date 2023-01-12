package domain.listeners

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener

class KeyboardListener : NativeKeyListener {

    private lateinit var callback: Callback

    fun ligar(callback: Callback) {
        this.callback = callback
        GlobalScreen.addNativeKeyListener(this)

    }

    fun desligar() {
        try {
            GlobalScreen.removeNativeKeyListener(this)
        } catch (nativeHookException: NativeHookException) {
            nativeHookException.printStackTrace()
        }
    }

    override fun nativeKeyTyped(nativeEvent: NativeKeyEvent?) {

        super.nativeKeyTyped(nativeEvent)
    }

    override fun nativeKeyPressed(nativeEvent: NativeKeyEvent?) {
        callback.tecladoPressionouTecla(nativeEvent!!.rawCode, NativeKeyEvent.getKeyText(nativeEvent.keyCode))

        super.nativeKeyPressed(nativeEvent)
    }

    override fun nativeKeyReleased(nativeEvent: NativeKeyEvent?) {
        callback.tecladoSoltouTecla(nativeEvent!!.rawCode, NativeKeyEvent.getKeyText(nativeEvent.keyCode))

        super.nativeKeyReleased(nativeEvent)
    }

    interface Callback {
        fun tecladoPressionouTecla(rawCode: Int, nomeTecla: String)
        fun tecladoSoltouTecla(rawCode: Int, nomeTecla: String)
    }
}

