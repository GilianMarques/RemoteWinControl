package perifericos.entrada

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener


class MouseListener : NativeMouseInputListener, NativeMouseWheelListener {

    private lateinit var callback: MouseCallback

    override fun nativeMousePressed(e: NativeMouseEvent) = callback.mouseBotaoPressionado(e.button)

    override fun nativeMouseReleased(e: NativeMouseEvent) = callback.mouseBotaoSolto(e.button)

    override fun nativeMouseMoved(e: NativeMouseEvent) = callback.mouseMoveu(e.x, e.y)

    override fun nativeMouseWheelMoved(nativeEvent: NativeMouseWheelEvent) =
        callback.mouseRolou(nativeEvent.wheelRotation)

    override fun nativeMouseDragged(e: NativeMouseEvent) = callback.mouseMoveu(e.x, e.y)


    fun ligar(callback: MouseCallback) {
        this.callback = callback

        GlobalScreen.addNativeMouseListener(this)
        GlobalScreen.addNativeMouseMotionListener(this)
        GlobalScreen.addNativeMouseWheelListener(this)
    }

    fun desligar() {

        GlobalScreen.removeNativeMouseListener(this)
        GlobalScreen.removeNativeMouseMotionListener(this)
        GlobalScreen.removeNativeMouseWheelListener(this)
    }

    interface MouseCallback {
        fun mouseBotaoPressionado(botao: Int)
        fun mouseBotaoSolto(botao: Int)
        fun mouseMoveu(x: Int, y: Int)
        fun mouseRolou(direcao: Int)
    }

}