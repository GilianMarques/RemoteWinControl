package domain.perifericos

import java.awt.Dimension
import java.awt.Toolkit


object Display {

    fun tamanhoDaTela() {
        val size: Dimension = Toolkit.getDefaultToolkit().screenSize

        val width = size.getWidth().toInt()
        val height = size.getHeight().toInt()

        println(
            "Current Screen size : width : $width height : $height"
        )
    }

    fun getCoordenadasRelativas(xAbsoluto: Int, yAbsoluto: Int): Pair<Int, Int> {
       // if (true) return xAbsoluto to yAbsoluto

        val size: Dimension = Toolkit.getDefaultToolkit().screenSize

        val width = size.getWidth().toInt() // x
        val height = size.getHeight().toInt() // y

        val xRelativo = (xAbsoluto.toFloat() * 100f) / width
        val yRelativo = (yAbsoluto.toFloat() * 100f) / height

        //  println("xRelativo $xRelativo%, yRelativo $yRelativo%")
        return xRelativo.toInt() to yRelativo.toInt()

    }

    fun getCoordenadasAbsolutas(xRelativo: Int, yRelativo: Int): Pair<Int, Int> {
      //  return xRelativo.toInt() to yRelativo.toInt()

        val size: Dimension = Toolkit.getDefaultToolkit().screenSize

        val width = size.getWidth().toInt() // x
        val height = size.getHeight().toInt() // y

        val xAbsoluto = (width / 100) * xRelativo
        val yAbsoluto = (height / 100) * yRelativo

        //  println("xRelativo $xRelativo%, yRelativo $yRelativo%")
        return xAbsoluto to yAbsoluto
    }

    fun printarMovimentoAbsoluto(x: Int, y: Int) {
        println("xAbsoluto $x px, yAbsoluto $y px")
    }
}