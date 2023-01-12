package gmarques.remotewincontrol.domain.volume

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class ServicoBotoesVolume : AccessibilityService() {

    companion object {
        var ignorarEventos = false
    }

    override fun onServiceConnected() {
        Log.d("USUK", "ServicoBotoesVolume.onServiceConnected: ")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {}

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (ignorarEventos) return super.onKeyEvent(event)
        when (event.keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                when (event.action) {
                    KeyEvent.ACTION_DOWN -> {
                        VolumeHandler.pressionarVolumeUp()
                    }
                    KeyEvent.ACTION_UP -> {
                        VolumeHandler.soltouVolumeUp()
                    }
                }
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                when (event.action) {
                    KeyEvent.ACTION_DOWN -> {
                        VolumeHandler.pressionarVolumeDown()
                    }
                    KeyEvent.ACTION_UP -> {
                        VolumeHandler.soltouVolumeDown()
                    }
                }
            }
        }
        return super.onKeyEvent(event)
    }

    override fun onInterrupt() {
        Log.d("USUK", "ServicoBotoesVolume.onInterrupt: ")
    }
}