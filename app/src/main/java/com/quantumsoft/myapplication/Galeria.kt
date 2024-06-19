package com.quantumsoft.myapplication

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Galeria(
    private val left: Float,
    private val top: Float,
    private val right: Float,
    private val bottom: Float,
    private var borderColor: Int
) : DibujarExposicion {

    private val drawableShapes = mutableListOf<DibujarExposicion>()

    fun addDrawableShape(drawableShape: DibujarExposicion, offsetX: Float = 0f, offsetY: Float = 0f) {
        // Calcular las posiciones relativas dentro de la galería
        val adjustedShape = adjustShapePosition(drawableShape, offsetX, offsetY)
        drawableShapes.add(adjustedShape)
    }

    private fun adjustShapePosition(shape: DibujarExposicion, offsetX: Float, offsetY: Float): DibujarExposicion {
        if (shape is Escultura) {
            val circle = shape as Escultura
            val adjustedX = clamp(circle.getX() + offsetX, left + circle.getRadius(), right - circle.getRadius())
            val adjustedY = clamp(circle.getY() + offsetY, top + circle.getRadius(), bottom - circle.getRadius())
            circle.setX(adjustedX)
            circle.setY(adjustedY)
            return circle
        } else if (shape is Pintura) {
            val rect = shape as Pintura
            val adjustedLeft = clamp(rect.getLeft() + offsetX, left, right - rect.getWidth())
            val adjustedTop = clamp(rect.getTop() + offsetY, top, bottom - rect.getHeight())
            rect.setLeft(adjustedLeft)
            rect.setTop(adjustedTop)
            val adjustedRight = adjustedLeft + rect.getWidth()
            val adjustedBottom = adjustedTop + rect.getHeight()
            rect.setRight(adjustedRight)
            rect.setBottom(adjustedBottom)
            return rect
        }
        return shape
    }

    private fun clamp(value: Float, min: Float, max: Float): Float {
        return Math.max(min, Math.min(max, value))
    }

    fun getTopLeft(): Pair<Float, Float> {
        return Pair(left, top)
    }

    fun getTopRight(): Pair<Float, Float> {
        return Pair(right, top)
    }

    fun getBottomLeft(): Pair<Float, Float> {
        return Pair(left, bottom)
    }

    fun getBottomRight(): Pair<Float, Float> {
        return Pair(right, bottom)
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        // Dibujar el rectángulo de la galería
        paint.color = borderColor
        canvas.drawRect(left, top, right, bottom, paint)

        // Dibujar las coordenadas de la galería
        paint.color = Color.DKGRAY
        paint.textSize = 30f
        canvas.drawText("($left, $top)", left, top - 10, paint)
        canvas.drawText("($right, $bottom)", right, bottom + 30, paint)

        // Dibujar las figuras dentro de la galería
        for (shape in drawableShapes) {
            shape.draw(canvas, paint)
        }
    }
}