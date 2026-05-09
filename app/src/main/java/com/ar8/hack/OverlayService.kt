package com.ar8.hack
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.view.*
import android.widget.Button
import android.widget.Toast
class OverlayService : Service() {
    override fun onBind(intent: Intent?) = null
    override fun onCreate() {
        super.onCreate()
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val view = LayoutInflater.from(this).inflate(R.layout.overlay_menu, null)
        val params = WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT)
        params.gravity = Gravity.TOP or Gravity.START
        view.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                params.x = event.rawX.toInt() - 150; params.y = event.rawY.toInt() - 150
                wm.updateViewLayout(view, params)
            }
            true
        }
        view.findViewById<Button>(R.id.btnDetect).setOnClickListener { Toast.makeText(this, "Scanning Balls... Autoplay Active", Toast.LENGTH_SHORT).show() }
        view.findViewById<Button>(R.id.btnClose).setOnClickListener { stopSelf() }
        wm.addView(view, params)
    }
    override fun onDestroy() { super.onDestroy() }
}
