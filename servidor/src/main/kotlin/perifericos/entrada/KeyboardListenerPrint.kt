package perifericos.entrada

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener

/**serve apenas pra ver os prints no log*/
class KeyboardListenerPrint : NativeKeyListener {


    fun ligar() {
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
        //captura o conteudo que pode ser digitado num campo de texto Ex: ao pressionar 'SHIFT+2' ira capturar '@'
        println(
            "Escreveu: '${nativeEvent?.keyChar}' " +
                    "keyCode: ${nativeEvent?.keyCode} " +
                    "location: ${nativeEvent?.keyLocation} " +
                    "rawCode: ${nativeEvent?.rawCode} "
        )

        super.nativeKeyTyped(nativeEvent)
    }

    override fun nativeKeyPressed(nativeEvent: NativeKeyEvent?) {

        println(
            "Pressionou: '${NativeKeyEvent.getKeyText(nativeEvent!!.keyCode)}' " +
                    "keyCode: ${nativeEvent.keyCode} " +
                    "location: ${nativeEvent.keyLocation} " +
                    "rawCode: ${nativeEvent.rawCode} "
        )

        super.nativeKeyPressed(nativeEvent)
    }

    override fun nativeKeyReleased(nativeEvent: NativeKeyEvent?) {


        //captura todas as teclas digitadas
        println(
            "Soltou: '${NativeKeyEvent.getKeyText(nativeEvent!!.keyCode)}' " +
                    "keyCode: ${nativeEvent.keyCode} " +
                    "location: ${nativeEvent.keyLocation} " +
                    "rawCode: ${nativeEvent.rawCode} "
        )

        super.nativeKeyReleased(nativeEvent)
    }

}

