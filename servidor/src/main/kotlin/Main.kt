import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException
import kotlinx.coroutines.*
import rede.io.RedeController
import kotlin.system.exitProcess

val globalScope = CoroutineScope(Job())

class Main {
    fun main(): Unit = runBlocking {

        initNativeHook()
        RedeController.iniciarServidorAsync()
        TrayIcon().criar()
    }


    private fun initNativeHook() {
        try {
            GlobalScreen.registerNativeHook()
        } catch (ex: NativeHookException) {
            System.err.println("There was a problem registering the native hook.")
            System.err.println(ex.message)

        }
    }
}
