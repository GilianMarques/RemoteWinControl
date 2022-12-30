package perifericos.entrada

import perifericos.DisplaySizeInfo
import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener


class MouseListener : NativeMouseInputListener, NativeMouseWheelListener {
    override fun nativeMouseClicked(e: NativeMouseEvent) {
        println("Mouse Clicked: " + e.clickCount)
    }

    override fun nativeMousePressed(e: NativeMouseEvent) {
        println("Mouse Pressed: " + e.button)
    }

    override fun nativeMouseReleased(e: NativeMouseEvent) {
        println("Mouse Released: " + e.button)
    }

    override fun nativeMouseMoved(e: NativeMouseEvent) {
        println("Mouse Moved: " + e.x + ", " + e.y)
        DisplaySizeInfo().printarMovimentoRelativo(e.x,e.y)
    }

    override fun nativeMouseDragged(e: NativeMouseEvent) {
        println("Mouse Dragged: " + e.x + ", " + e.y)
    }

    override fun nativeMouseWheelMoved(nativeEvent: NativeMouseWheelEvent) {
        println(
            "Mosue Wheel Moved: wheelRotation: ${nativeEvent.wheelRotation}" +
                    " wheelDirection: ${nativeEvent.wheelDirection} " +
                    " scrollAmount: ${nativeEvent.scrollAmount}" +
                    " scrollType: ${nativeEvent.scrollType}"
        )
        super.nativeMouseWheelMoved(nativeEvent)
    }

    fun init() {

        GlobalScreen.addNativeMouseListener(this)
        GlobalScreen.addNativeMouseMotionListener(this)
        GlobalScreen.addNativeMouseWheelListener(this)
    }

}