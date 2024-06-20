package com.quantumsoft.myapplication.Canvas

import android.graphics.Canvas
import android.graphics.Paint

class Gallery(
    override val x: Float,
    override val y: Float,
    override val width: Float,
    override val height: Float,
    val name: String,
    private val paint: Paint,
    private val textPaint: Paint
) : DrawableItem {

    private val items = mutableListOf<DrawableItem>()

    fun addItem(item: DrawableItem) {
        items.add(item)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(x, y, x + width, y + height, paint)
        canvas.drawText(name, x + width / 2, y + height / 2, textPaint)
        for (item in items) {
            item.draw(canvas)
        }
    }

    override fun containsPoint(px: Float, py: Float): Boolean {
        return px in x..(x + width) && py in y..(y + height)
    }

    // Verifica si la galería está visible en el área de recorte
    fun isVisibleInClip(clipX: Float, clipY: Float, clipWidth: Float, clipHeight: Float): Boolean {
        return x < clipX + clipWidth && x + width > clipX &&
                y < clipY + clipHeight && y + height > clipY
    }


    // Métodos para posicionamiento relativo
    fun positionBelow(other: Gallery, padding: Float = 0f): Pair<Float, Float> {
        return Pair(other.x, other.y + other.height + padding)
    }

    fun positionRightOf(other: Gallery, padding: Float = 0f): Pair<Float, Float> {
        return Pair(other.x + other.width + padding, other.y)
    }

    fun positionLeftOf(other: Gallery, padding: Float = 0f): Pair<Float, Float> {
        return Pair(other.x - this.width - padding, other.y)
    }

    fun positionAbove(other: Gallery, padding: Float = 0f): Pair<Float, Float> {
        return Pair(other.x, other.y - this.height - padding)
    }
}