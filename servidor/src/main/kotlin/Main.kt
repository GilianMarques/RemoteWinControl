import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException
import kotlinx.coroutines.*
import perifericos.DisplaySizeInfo
import perifericos.entrada.KeyboardListener
import perifericos.entrada.MouseListener
import servidor.Cliente
import servidor.Servidor
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import kotlin.system.exitProcess

fun main(): Unit = runBlocking {

    //  initTestes()
    launch {
        println("ligando servidor")
        Servidor.ligar()
    }

    launch {
        println("ligando cliente")
        Cliente.ligar()

    }

    Gui().exibir()

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
