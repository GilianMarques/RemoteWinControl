package gmarques.remotewincontrol.presenter

import android.content.ComponentName
import android.content.Context
import android.provider.Settings.Secure
import gmarques.remotewincontrol.presenter.servicos.VolumeKeysListener

class Permissoes {

    fun permissaoDeAcessibilidadeConcedida(context: Context): Boolean {
        try {

            val permConcedida = Secure.getInt(context.contentResolver, Secure.ACCESSIBILITY_ENABLED)

            return if (permConcedida == 1) {

                val nomeServico = ComponentName(context, VolumeKeysListener::class.java)
                    .flattenToString()
                val servicosPermitidos = Secure
                    .getString(context.contentResolver, Secure.ENABLED_ACCESSIBILITY_SERVICES)
                servicosPermitidos?.contains(nomeServico) ?: false

            } else false

        } catch (ex: Throwable) {
            ex.printStackTrace()
            return false
        }

    }

}