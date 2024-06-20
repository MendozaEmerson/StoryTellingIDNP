package com.quantumsoft.myapplication.Canvas

import android.graphics.Canvas
import android.graphics.Paint

class Sculpture(
    override val x: Float,
    override val y: Float,
    private val radius: Float,
    private val name: String,
    private val paint: Paint,
    private val textPaint: Paint
) : DrawableItem {

    override val width: Float = radius * 2
    override val height: Float = radius * 2

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x + radius, y + radius, radius, paint)
        canvas.drawText(name, x + radius, y + radius, textPaint)
    }

    override fun containsPoint(px: Float, py: Float): Boolean {
        val dx = px - (x + radius)
        val dy = py - (y + radius)
        return dx * dx + dy * dy <= radius * radius
    }
}