package com.quantumsoft.myapplication.ui.canvas

import android.graphics.Canvas

interface DrawableItem {
    val x: Float
    val y: Float
    val width: Float
    val height: Float

    fun draw(canvas: Canvas)
    fun containsPoint(x: Float, y: Float): Boolean
}