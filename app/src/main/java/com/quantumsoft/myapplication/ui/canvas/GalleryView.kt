package com.quantumsoft.myapplication.ui.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class GalleryView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr) {

    private val galleryTextPaint = Paint().apply {
        color = Color.WHITE
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }
    private var gallery: Gallery? = Gallery(420f, 1450f, 480f, 250f,
        "La sala",
        Paint().apply { color = Color.parseColor("#9AC7BF")},
        galleryTextPaint)


        fun setGallery(gallery: Gallery) {
            this.gallery = gallery
            invalidate() // Solicita redibujar la vista
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            gallery?.let { gallery ->
                // Calcula la escala necesaria
                val scaleX = width / gallery.width
                val scaleY = height / gallery.height
                val scale = minOf(scaleX, scaleY)

                // Calcula las nuevas dimensiones escaladas
                val scaledWidth = gallery.width * scale
                val scaledHeight = gallery.height * scale

                // Guarda el estado actual del canvas
                canvas.save()

                // Aplica la escala al canvas
                canvas.scale(scale, scale)

                // Dibuja el gallery escalado
                gallery.draw(canvas)

                // Restaura el estado del canvas
                canvas.restore()
            }
        }
    }
