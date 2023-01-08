package comandos_remotos

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.Robot
import java.awt.event.InputEvent


object Mouse {

    private val mouse = Robot()
    private val scope = CoroutineScope(Job())

    fun cliqueEsq() = scope.launch {
        mouse.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        delay(10)
        mouse.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }


    fun cliqueDir() = scope.launch {
        mouse.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        delay(10)
        mouse.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    fun cliqueCen() = scope.launch {
        mouse.mousePress(InputEvent.BUTTON2_DOWN_MASK);
        delay(10)
        mouse.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
    }

    fun mover(metadata: FloatArray) = scope.launch {

        val x = metadata[0]
        val y = metadata[1]

        Cmd.run("nircmd.exe movecursor $x $y")

    }

    fun rolar(metadata: FloatArray) {
        val dir = metadata.first()
        mouse.mouseWheel(dir.toInt())
    }

}