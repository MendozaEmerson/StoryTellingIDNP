package com.quantumsoft.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CanvasMap(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private val drawableShapes = mutableListOf<DrawableShape>()

    fun addDrawableShape(drawableShape: DrawableShape) {
        drawableShapes.add(drawableShape)
        invalidate() // Llama a onDraw para redibujar la vista
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (shape in drawableShapes) {
            shape.draw(canvas, paint)
        }
    }
}