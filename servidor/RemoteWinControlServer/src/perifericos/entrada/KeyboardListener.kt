package perifericos.entrada

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener

class KeyboardListener : NativeKeyListener {

    override fun nativeKeyTyped(nativeEvent: NativeKeyEvent?) {
        //captura o conteudo que pode ser digitado num campo de texto Ex: ao pressionar 'SHIFT+2' ira capturar '@'
        println(
            "Escreveu: '${nativeEvent?.keyChar}' " +
                    "keyCode: ${nativeEvent?.keyCode} " +
                    "location: ${nativeEvent?.keyLocation} " +
                    "rawCode: ${nativeEvent?.rawCode} "
        )

        if (nativeEvent?.keyCode == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook()
            } catch (nativeHookException: NativeHookException) {
                nativeHookException.printStackTrace()
            }
        }

        super.nativeKeyTyped(nativeEvent)
    }

    override fun nativeKeyReleased(nativeEvent: NativeKeyEvent?) {

        //captura todas as teclas digitadas
        println(
            "Pressionou: '${NativeKeyEvent.getKeyText(nativeEvent!!.keyCode)}' " +
                    "keyCode: ${nativeEvent.keyCode} " +
                    "location: ${nativeEvent.keyLocation} " +
                    "rawCode: ${nativeEvent.rawCode} "
        )

        super.nativeKeyReleased(nativeEvent)
    }

    fun init() {

        GlobalScreen.addNativeKeyListener(this)
    }
}