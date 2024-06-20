package com.quantumsoft.myapplication

import android.graphics.Canvas
import android.graphics.Color
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

        val borderPaint = Paint(textPaint)
        borderPaint.color = Color.BLACK
        borderPaint.textSize = textPaint.textSize
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = 8f
        borderPaint.isAntiAlias = true


        canvas.drawText(name, x + radius, y + radius, borderPaint)
        canvas.drawText(name, x + radius, y + radius, textPaint)
    }

    override fun containsPoint(x: Float, y: Float): Boolean {
        val dx = x - (this.x + radius)
        val dy = y - (this.y + radius)
        return dx * dx + dy * dy <= radius * radius
    }
}
