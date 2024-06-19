package com.quantumsoft.myapplication

import android.graphics.Canvas
import android.graphics.Paint

class Escultura(
    private var centerX: Float,
    private var centerY: Float,
    private val radius: Float,
    private var color: Int
) : DibujarExposicion {

    fun getX(): Float {
        return centerX
    }

    fun setX(x: Float) {
        centerX = x
    }

    fun getY(): Float {
        return centerY
    }

    fun setY(y: Float) {
        centerY = y
    }

    fun getRadius(): Float {
        return radius
    }

    fun getColor(): Int {
        return color
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        paint.color = color
        canvas.drawCircle(centerX, centerY, radius, paint)
    }
}
