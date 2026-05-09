package com.ar8.hack
import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
class AutoplayService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
    override fun onInterrupt() {}
}
