import rede.io.EnderecosDeRede
import rede.io.RedeController
import java.awt.*
import java.awt.TrayIcon
import javax.imageio.ImageIO
import kotlin.system.exitProcess


class TrayIcon {

    init {
        if (!SystemTray.isSupported()) {
            println("Icone de bandeja não é suportado");
        }
    }

     suspend fun criar() {

        val trayIcon = TrayIcon(carregarIcone())

        trayIcon.popupMenu = criarMenu()
        trayIcon.toolTip = carregarDica()

        try {
            SystemTray.getSystemTray().add(trayIcon)
        } catch (e: AWTException) {
            println("Erro adicionando icone de bandeja")
        }
    }

    private suspend fun carregarDica(): String {
        return "RemoteWinControl (ServerSide)" +
                " IP: ${EnderecosDeRede.lerIpDaRede()}" +
                " Porta: ${RedeController.getPortaDoServidor()}"
    }

    private fun criarMenu(): PopupMenu {

        val popup = PopupMenu()

        popup.add(MenuItem("Sair").apply {
            this.addActionListener {
                exitProcess(0)
            }
        })

        return popup
    }

    private fun carregarIcone(): Image {
        return ImageIO.read(this@TrayIcon.javaClass.getResource("/icone_bandeja_16x16.png"));

    }
}