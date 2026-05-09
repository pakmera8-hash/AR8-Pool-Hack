package com.ar8.hack

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class LineView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 5f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // This draws a crosshair/line in the middle of the screen
        val centerX = width / 2f
        val centerY = height / 2f
        
        // Horizontal guide
        canvas.drawLine(0f, centerY, width.toFloat(), centerY, paint)
        // Vertical guide
        canvas.drawLine(centerX, 0f, centerX, height.toFloat(), paint)
        
        // Circular aim assist
        canvas.drawCircle(centerX, centerY, 100f, paint)
    }
}