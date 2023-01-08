import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException
import kotlinx.coroutines.*
import perifericos.DisplaySizeInfo
import perifericos.entrada.KeyboardListener
import perifericos.entrada.MouseListener
import rede.RedeAdapter
import rede.io.Servidor
import kotlin.system.exitProcess

fun main(): Unit = runBlocking {

    launch {
        Servidor.ligar()
    }

    RedeAdapter()

    //   Gui().exibir()

}


fun initTestes() {
    try {
        GlobalScreen.registerNativeHook()
    } catch (ex: NativeHookException) {
        System.err.println("There was a problem registering the native hook.")
        System.err.println(ex.message)
        exitProcess(1)
    }

    val listener = KeyboardListener()
    listener.init()

    val mouseListener = MouseListener()
    mouseListener.init()


    val sManager = DisplaySizeInfo()

    sManager.tamanhoDaTela()
}
