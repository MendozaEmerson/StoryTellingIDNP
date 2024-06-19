package com.quantumsoft.myapplication

import android.graphics.Canvas
import android.graphics.Paint

class Galeria(
    private val left: Float,
    private val top: Float,
    private val right: Float,
    private val bottom: Float,
    private var borderColor: Int
) : DibujarExposicion {

    private val dibujarExposicions = mutableListOf<DibujarExposicion>()

    fun addDrawableShape(dibujarExposicion: DibujarExposicion, offsetX: Float = 0f, offsetY: Float = 0f) {
        // Calcular las posiciones relativas dentro de la galería
        val adjustedShape = adjustShapePosition(dibujarExposicion, offsetX, offsetY)
        dibujarExposicions.add(adjustedShape)
    }

    private fun adjustShapePosition(shape: DibujarExposicion, offsetX: Float, offsetY: Float): DibujarExposicion {
        // Ajustar la posición de la figura para que esté dentro de los límites de la galería
        if (shape is Escultura) {
            val escultura = shape as Escultura
            val adjustedX = clamp(escultura.getX() + offsetX, left + escultura.getRadius(), right - escultura.getRadius())
            val adjustedY = clamp(escultura.getY() + offsetY, top + escultura.getRadius(), bottom - escultura.getRadius())
            escultura.setX(adjustedX)
            escultura.setY(adjustedY)
            return escultura
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
        for (shape in dibujarExposicions) {
            shape.draw(canvas, paint)
        }
    }
}