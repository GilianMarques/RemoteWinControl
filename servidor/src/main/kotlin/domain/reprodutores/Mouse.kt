package domain.reprodutores

import domain.dtos.cliente.DtoCliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.MouseInfo
import java.awt.Robot
import java.awt.event.InputEvent


object Mouse {

    private var mouseY: Int = 0
    private var mouseX: Int = 0
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

    fun mover(cmd: DtoCliente) = scope.launch {
        Cmd.run("nircmd.exe movecursor ${cmd.getFloat("movX")} ${cmd.getFloat("movY")}")
    }

    fun rolar(cmd: DtoCliente) = mouse.mouseWheel(cmd.getInt("direcao"))
    fun salvarPosicaoAtualDoMouse() {
        mouseX = MouseInfo.getPointerInfo().getLocation().x
        mouseY = MouseInfo.getPointerInfo().getLocation().y
    }

    fun restaurarPosicaoOriginalDoMouse() {
        mouse.mouseMove(mouseX, mouseY)
    }


}