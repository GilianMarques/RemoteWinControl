package gmarques.remotewincontrol.presenter.volume

import android.accessibilityservice.AccessibilityService
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class ServicoBotoesVolume : AccessibilityService() {

    override fun onServiceConnected() {}

    override fun onAccessibilityEvent(event: AccessibilityEvent) {}

    private val handler = VolumeHandler()

    override fun onKeyEvent(event: KeyEvent): Boolean {
        when (event.keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                when (event.action) {
                    KeyEvent.ACTION_DOWN -> {
                        handler.pressionouVolumeUp()
                    }
                    KeyEvent.ACTION_UP -> {
                        handler.soltouVolumeUp()
                        VolumeHelper.setarVolumeOriginal()
                    }
                }
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                when (event.action) {
                    KeyEvent.ACTION_DOWN -> {
                        handler.pressionouVolumeDown()
                    }
                    KeyEvent.ACTION_UP -> {
                        handler.soltouVolumeDown()
                        VolumeHelper.setarVolumeOriginal()
                    }
                }
            }
        }
        return super.onKeyEvent(event)
    }

    override fun onInterrupt() {}
}