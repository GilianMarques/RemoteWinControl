package perifericos

import java.awt.Dimension
import java.awt.Toolkit


class DisplaySizeInfo {

    fun tamanhoDaTela() {
        val size: Dimension = Toolkit.getDefaultToolkit().screenSize

        val width = size.getWidth().toInt()
        val height = size.getHeight().toInt()

        println(
            "Current Screen size : width : $width height : $height"
        )
    }

    fun printarMovimentoRelativo(x: Int, y: Int) {

        val size: Dimension = Toolkit.getDefaultToolkit().screenSize

        val width = size.getWidth().toInt() // x
        val height = size.getHeight().toInt() // y

        val xRelativo = (x.toFloat() * 100f) / width.toFloat()
        val yRelativo = (y.toFloat() * 100f) / height.toFloat()

        println("xRelativo $xRelativo%, yRelativo $yRelativo%")
    }

    fun printarMovimentoAbsoluto(x: Int, y: Int) {
        println("xAbsoluto $x px, yAbsoluto $y px")
    }
}