package com.quantumsoft.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
class MuseumView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val TAG = "MuseumView"

    private val museum = Museum()

    // Escalado y desplazamiento del canvas
    private var scale = 1f
    private var canvasTranslateX = 0f
    private var canvasTranslateY = 0f

    // Punto central del zoom
    private var zoomCenterX = 0f
    private var zoomCenterY = 0f

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
        clickedGallery?.let {
            if (it.width> it.height) {
                scale = canvas.height / it.height
            } else {
                scale = canvas.width / it.width
            }
            canvasTranslateX = -it.x - it.width / 2 + canvas.width / 2
            canvasTranslateY = -it.y - it.height / 2 + canvas.height / 2
        }

//        canvas.scale(scale, scale, canvas.width / 2f, canvas.height / 2f)
        canvas.translate(canvasTranslateX, canvasTranslateY)
        Log.d(TAG, "onDraw: scale: $scale, translateX: $canvasTranslateX, translateY: $canvasTranslateY")
        museum.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d(TAG, "x: ${event.x}, y: ${event.y}")
        Log.i(TAG, "scale: $scale, translationX: $canvasTranslateX, translationY: $canvasTranslateY")
        val scaledX = (event.x - canvasTranslateX) / scale
        val scaledY = (event.y - canvasTranslateY) / scale

        // various combinations of event position, translation and scale
        val x1 = (event.x + canvasTranslateX/scale) / scale
        val y1 = (event.y + canvasTranslateY/scale) / scale
        Log.i(TAG, "x1: $x1, y1: $y1")
        val x2 = (event.x + canvasTranslateX) * scale
        val y2 = (event.y + canvasTranslateY) * scale
        Log.i(TAG, "x2: $x2, y2: $y2")
        val x3 = (event.x * scale) + canvasTranslateX
        val y3 = (event.y * scale) + canvasTranslateY
        Log.i(TAG, "x3: $x3, y3: $y3")
        val x4 = (event.x * scale) - canvasTranslateX
        val y4 = (event.y * scale) - canvasTranslateY
        Log.i(TAG, "x4: $x4, y4: $y4")
        val x5 = (event.x - canvasTranslateX) / scale
        val y5 = (event.y - canvasTranslateY) / scale
        Log.i(TAG, "x5: $x5, y5: $y5")

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (clickedGallery != null) {
                    // Si hay una galería clickeada, restaurar el zoom normal
                    scale = 1f
                    canvasTranslateX = 0f
                    canvasTranslateY = 0f
                    clickedGallery = null
                    invalidate()
                    return true
                }
                Log.d(TAG, "onTouchEvent: Down at ($scaledX, $scaledY)")
                val clickedGallery = findClickedGallery(scaledX, scaledY)
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
        Log.i(TAG, "findClickedGallery: x: $x y: $y")
        for (gallery in museum.galleries) {
            Log.i(TAG, "findClickedGallery: ${gallery.name} x: ${gallery.x} y: ${gallery.y}")
            if (gallery.containsPoint(x, y)) {
                Log.d(TAG, "findClickedGallery: Clicked on ${gallery.name}")
                return gallery
            }
        }
        return null
    }

    // Método para alternar el zoom sobre una galería
    private fun toggleZoom(gallery: Gallery) {
        if (gallery == clickedGallery) {
            // Si la galería clickeada es la misma que la última clickeada, restaurar el zoom normal
            scale = 1f
            canvasTranslateX = 0f
            canvasTranslateY = 0f
            clickedGallery = null
        } else {
            // Hacer zoom sobre la galería clickeada
            scale = 1.2f  // Ejemplo de escala de zoom, ajustar según sea necesario
//            canvasTranslateX = -gallery.x + width / 2 - gallery.width / 2
//            canvasTranslateY = -gallery.y + height / 2 - gallery.height / 2
            clickedGallery = gallery
        }
        invalidate()
    }
}