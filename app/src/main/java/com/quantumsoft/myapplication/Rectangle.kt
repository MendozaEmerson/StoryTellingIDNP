package com.quantumsoft.myapplication

import android.graphics.Canvas
import android.graphics.Paint

class Rectangle(
    private var left: Float,
    private var top: Float,
    private var right: Float,
    private var bottom: Float,
    private var color: Int
) : DrawableShape {

    fun getWidth(): Float {
        return right - left
    }

    fun getHeight(): Float {
        return bottom - top
    }

    fun getLeft(): Float {
        return left
    }

    fun setLeft(left: Float) {
        this.left = left
    }

    fun getTop(): Float {
        return top
    }

    fun setTop(top: Float) {
        this.top = top
    }

    fun getRight(): Float {
        return right
    }

    fun setRight(right: Float) {
        this.right = right
    }

    fun getBottom(): Float {
        return bottom
    }

    fun setBottom(bottom: Float) {
        this.bottom = bottom
    }

    fun getColor(): Int {
        return color
    }

    fun setColor(color: Int) {
        this.color = color
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        paint.color = color
        canvas.drawRect(left, top, right, bottom, paint)
    }
}
