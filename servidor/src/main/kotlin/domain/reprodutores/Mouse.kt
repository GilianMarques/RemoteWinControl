package domain.reprodutores

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rede.dtos.cliente.DtoClienteMouseMover
import rede.dtos.cliente.DtoClienteMouseRolar
import java.awt.Robot
import java.awt.event.InputEvent


object Mouse {

    private val mouse = Robot()
    private val scope = CoroutineScope(Job())

    // TODO: cliquyes devem conter coodenadas x e y
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

    // TODO: ver como se comportar sem lançar ca corotina
    fun mover(cmd: DtoClienteMouseMover) = scope.launch {
        Cmd.run("nircmd.exe movecursor ${cmd.movX} ${cmd.movY}")
    }

    fun rolar(cmd: DtoClienteMouseRolar) = mouse.mouseWheel(cmd.dir)


}