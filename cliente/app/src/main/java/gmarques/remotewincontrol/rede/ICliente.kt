package gmarques.remotewincontrol.rede

interface ICliente {

    suspend fun enviarMsg(mensagem: String)

}