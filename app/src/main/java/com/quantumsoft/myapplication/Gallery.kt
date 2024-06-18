package com.quantumsoft.myapplication

import android.graphics.Canvas
import android.graphics.Paint

class Gallery(
    private val left: Float,
    private val top: Float,
    private val right: Float,
    private val bottom: Float,
    private var borderColor: Int
) : DrawableShape {

    private val drawableShapes = mutableListOf<DrawableShape>()

    fun addDrawableShape(drawableShape: DrawableShape, offsetX: Float = 0f, offsetY: Float = 0f) {
        // Calcular las posiciones relativas dentro de la galería
        val adjustedShape = adjustShapePosition(drawableShape, offsetX, offsetY)
        drawableShapes.add(adjustedShape)
    }

    private fun adjustShapePosition(shape: DrawableShape, offsetX: Float, offsetY: Float): DrawableShape {
        // Ajustar la posición de la figura para que esté dentro de los límites de la galería
        if (shape is Circle) {
            val circle = shape as Circle
            val adjustedX = clamp(circle.getX() + offsetX, left + circle.getRadius(), right - circle.getRadius())
            val adjustedY = clamp(circle.getY() + offsetY, top + circle.getRadius(), bottom - circle.getRadius())
            circle.setX(adjustedX)
            circle.setY(adjustedY)
            return circle
        } else if (shape is Rectangle) {
            val rect = shape as Rectangle
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
        // Puedes agregar ajustes para otros tipos de figuras según sea necesario
        return shape
    }

    private fun clamp(value: Float, min: Float, max: Float): Float {
        return Math.max(min, Math.min(max, value))
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        // Dibujar el rectángulo de la galería
        paint.color = borderColor
        canvas.drawRect(left, top, right, bottom, paint)

        // Dibujar las figuras dentro de la galería
        for (shape in drawableShapes) {
            shape.draw(canvas, paint)
        }
    }
}