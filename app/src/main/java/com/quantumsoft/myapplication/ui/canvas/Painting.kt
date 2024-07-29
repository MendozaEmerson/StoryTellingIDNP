package com.quantumsoft.myapplication.ui.canvas

import android.graphics.Canvas
import android.graphics.Paint

class Painting(
    override val x: Float,
    override val y: Float,
    override val width: Float,
    override val height: Float,
    private val name: String,
    private val paint: Paint,
    private val textPaint: Paint
) : DrawableItem {

    override fun draw(canvas: Canvas) {
        canvas.drawRect(x, y, x + width, y + height, paint)
        canvas.drawText(name, x + width / 2, y + height / 2, textPaint)
    }

    override fun containsPoint(px: Float, py: Float): Boolean {
        return px in x..(x + width) && py in y..(y + height)
    }
}