package com.ar8.hack

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: TextView // We will change this to a Canvas later for the lines

    override fun onBind(intent: Intent?): IBinder? {
        return null // We don't bind this service; it runs freely in the background
    }

    override fun onCreate() {
        super.onCreate()
        
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Create the invisible layer that sits on top of everything
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            // TYPE_APPLICATION_OVERLAY is what allows it to draw over the game
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )

        // Put the overlay at the top-left corner
        layoutParams.gravity = Gravity.TOP or Gravity.LEFT
        layoutParams.x = 50
        layoutParams.y = 50

        // For now, it's just a text indicator. 
        // Later, we will inject the C++ Physics Engine Canvas here.
        overlayView = TextView(this).apply {
            text = "AR8 HACK: ACTIVE"
            setTextColor(Color.GREEN)
            textSize = 20f
            setPadding(10, 10, 10, 10)
            setBackgroundColor(Color.argb(150, 0, 0, 0)) // Semi-transparent black box
        }

        // Add the view to the screen
        windowManager.addView(overlayView, layoutParams)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up the overlay if the app closes
        if (::overlayView.isInitialized) {
            windowManager.removeView(overlayView)
        }
    }
}