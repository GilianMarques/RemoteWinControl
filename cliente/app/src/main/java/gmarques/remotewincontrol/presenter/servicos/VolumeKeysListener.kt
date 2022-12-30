package gmarques.remotewincontrol.presenter.servicos

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class VolumeKeysListener : AccessibilityService() {

    override fun onServiceConnected() {}

    override fun onAccessibilityEvent(event: AccessibilityEvent) {}

    override fun onKeyEvent(event: KeyEvent): Boolean {
        when (event.keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                when (event.action) {
                    KeyEvent.ACTION_DOWN -> {
                        Log.d("USUK", "KeyService.onKeyEvent: KEYCODE_VOLUME_UP ACTION_DOWN")
                    }
                    KeyEvent.ACTION_UP -> {
                        Log.d("USUK", "KeyService.onKeyEvent: KEYCODE_VOLUME_UP ACTION_UP")
                        // TODO:        MainFragment.volume()
                    }
                }
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                when (event.action) {
                    KeyEvent.ACTION_DOWN -> {
                        Log.d("USUK", "KeyService.onKeyEvent: KEYCODE_VOLUME_DOWN ACTION_DOWN")
                    }
                    KeyEvent.ACTION_UP -> {
                        Log.d("USUK", "KeyService.onKeyEvent: KEYCODE_VOLUME_DOWN ACTION_UP")
                        // TODO:      MainFragment.volume()
                    }
                }
            }
        }
        return super.onKeyEvent(event)
    }

    override fun onInterrupt() {}
}