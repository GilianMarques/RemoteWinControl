import mdlaf.MaterialLookAndFeel
import mdlaf.themes.MaterialLiteTheme
import java.awt.Dimension
import javax.swing.*

class Gui : JPanel() {

    private val btnSalvar: JButton = JButton("Salvar")
    private val tfIp: JTextField = JTextField(5)
    private val lblIp: JLabel = JLabel("Endereço de IP")
    private val tfPorta: JTextField = JTextField(5)
    private val lblPorta: JLabel = JLabel("Porta")
    private val btnRedefinir: JButton = JButton("Redefinir")

    init {
        //construct components

        //adjust size and set layout
        preferredSize = Dimension(400, 230)
        layout = null

        //add components
        add(btnSalvar)
        add(tfIp)
        add(lblIp)
        add(tfPorta)
        add(lblPorta)
        add(btnRedefinir)

        //set component bounds (only needed by Absolute Positioning)
        tfIp.setBounds(25, 50, 350, 25)
        lblIp.setBounds(25, 20, 100, 25)

        tfPorta.setBounds(25, 115, 350, 25)
        lblPorta.setBounds(25, 85, 100, 25)

        btnRedefinir.setBounds(225, 175, 150, 25)
        btnSalvar.setBounds(25, 175, 150, 25)

    }

    fun exibir() {
        try {
            UIManager.setLookAndFeel(MaterialLookAndFeel(MaterialLiteTheme()));
            // by including the https://github.com/material-ui-swing/DarkStackOverflowTheme
           // UIManager.setLookAndFeel(MaterialLookAndFeel(DarkStackOverflowTheme()));
        } catch (e: UnsupportedLookAndFeelException) {
            e.printStackTrace();
        }


        val frame = JFrame("Configuração de conexão")
        frame.defaultCloseOperation = JFrame.HIDE_ON_CLOSE
        frame.contentPane.add(Gui())
        frame.pack()
        frame.isVisible = true
    }


}