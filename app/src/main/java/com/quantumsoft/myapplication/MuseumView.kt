package com.quantumsoft.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MuseumView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val museum = Museum()

    // Parámetros de recorte y escalado
    private var clipX: Float = 0f
    private var clipY: Float = 0f
    private var clipWidth: Float = 1000f
    private var clipHeight: Float = 1000f

    // Galería actualmente clickeada para zoom
    private var clickedGallery: Gallery? = null

    init {

        // Estilos para esculturas y pinturas
        val sculpturePaint = Paint().apply {
            color = Color.BLUE
            style = Paint.Style.FILL
        }
        val sculptureTextPaint = Paint().apply {
            color = Color.WHITE
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }
        val paintingPaint = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.FILL
        }
        val paintingTextPaint = Paint().apply {
            color = Color.BLACK
            textSize = 30f
            textAlign = Paint.Align.CENTER
        }

        // Estilo para las galerías
        val galleryPaint = Paint().apply {
            color = Color.LTGRAY
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }
        val galleryTextPaint = Paint().apply {
            color = Color.RED
            textSize = 50f
            textAlign = Paint.Align.CENTER
        }

        // Ejemplo de creación de galerías
        val gallery1 = Gallery(50f, 50f, 400f, 400f, "Galería 1", galleryPaint, galleryTextPaint)
        gallery1.addItem(Sculpture(150f, 150f, 50f, "Escultura 1", sculpturePaint, sculptureTextPaint))
        gallery1.addItem(Painting(50f, 250f, 150f, 100f, "Pintura 1", paintingPaint, paintingTextPaint))
        museum.addGallery(gallery1)

        val (x2, y2) = gallery1.positionRightOf(gallery1, padding = 20f)
        val gallery2 = Gallery(x2, y2, 400f, 400f, "Galería 2", galleryPaint, galleryTextPaint)
        gallery2.addItem(Sculpture(150f, 150f, 60f, "Escultura 2", sculpturePaint, sculptureTextPaint))
        gallery2.addItem(Painting(50f, 250f, 200f, 100f, "Pintura 2", paintingPaint, paintingTextPaint))
        museum.addGallery(gallery2)

        val (x3, y3) = gallery1.positionBelow(gallery1, padding = 20f)
        val gallery3 = Gallery(x3, y3, 400f, 400f, "Galería 3", galleryPaint, galleryTextPaint)
        gallery3.addItem(Sculpture(150f, 150f, 50f, "Escultura 3", sculpturePaint, sculptureTextPaint))
        gallery3.addItem(Painting(50f, 250f, 150f, 100f, "Pintura 3", paintingPaint, paintingTextPaint))
        museum.addGallery(gallery3)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        museum.setClipArea(clipX, clipY, clipWidth, clipHeight)
        museum.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y
                clickedGallery = findClickedGallery(x, y)
                clickedGallery?.let {
                    toggleZoom(it)
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    // Método para encontrar la galería clickeada en las coordenadas (x, y)
    private fun findClickedGallery(x: Float, y: Float): Gallery? {
        for (gallery in museum.galleries) {
            if (gallery.containsPoint(x, y)) {
                return gallery
            }
        }
        return null
    }

    // Método para alternar el zoom sobre una galería
    private fun toggleZoom(gallery: Gallery) {
        if (gallery == clickedGallery) {
            // Si la galería clickeada es la misma que la última clickeada, restaurar el zoom normal
            clipX = 0f
            clipY = 0f
            clipWidth = 1000f
            clipHeight = 1000f
            clickedGallery = null
        } else {
            // Hacer zoom sobre la galería clickeada
            clipX = gallery.x
            clipY = gallery.y
            clipWidth = gallery.width
            clipHeight = gallery.height
            clickedGallery = gallery
        }
        invalidate()
    }

    // Métodos adicionales para configuración desde XML
    fun getClipX(): Float {
        return clipX
    }

    fun setClipX(clipX: Float) {
        this.clipX = clipX
        invalidate()
    }

    fun getClipY(): Float {
        return clipY
    }

    fun setClipY(clipY: Float) {
        this.clipY = clipY
        invalidate()
    }

    fun getClipWidth(): Float {
        return clipWidth
    }

    fun setClipWidth(clipWidth: Float) {
        this.clipWidth = clipWidth
        invalidate()
    }

    fun getClipHeight(): Float {
        return clipHeight
    }

    fun setClipHeight(clipHeight: Float) {
        this.clipHeight = clipHeight
        invalidate()
    }
}
