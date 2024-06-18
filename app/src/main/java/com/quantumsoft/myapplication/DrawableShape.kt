package com.quantumsoft.myapplication

import android.graphics.Canvas
import android.graphics.Paint

interface DrawableShape {
    fun draw(canvas: Canvas, paint: Paint)
}
