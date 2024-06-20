package com.quantumsoft.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
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
        val gallery1 = Gallery(0f, 0f, 200f, 200f,
            "",
            Paint().apply { color = Color.parseColor("#7EA6E0") },
            galleryTextPaint)
        museum.addGallery(gallery1)

        val (x2, y2) = gallery1.positionBelow(gallery1)
        val gallery2 = Gallery(x2, y2, 200f, 400f,
            "G4",
            Paint().apply { color = Color.parseColor("#A20025")},
            galleryTextPaint)
        museum.addGallery(gallery2)

        val (x3, y3) = gallery2.positionBelow(gallery2)
        val gallery3 = Gallery(x3, y3, 200f, 300f,
            "G5",
            Paint().apply { color = Color.parseColor("#F8CECC")},
            galleryTextPaint)
        museum.addGallery(gallery3)

        val gallery4 = Gallery(400f, 0f, 700f, 200f,
            "G1",
            Paint().apply { color = Color.parseColor("#FFF2CC")},
            galleryTextPaint)
        museum.addGallery(gallery4)

        val gallery5 = Gallery(900f, 200f, 200f, 650f,
            "G2",
            Paint().apply { color = Color.parseColor("#1BA1E2")},
            galleryTextPaint)
        museum.addGallery(gallery5)

        val gallery6 = Gallery(400f, 550f, 500f, 250f,
            "G3",
            Paint().apply { color = Color.parseColor("#D5739D")},
            galleryTextPaint)
        museum.addGallery(gallery6)

        val (x7, y7) = gallery6.positionBelow(gallery6)
        val gallery7 = Gallery(x7, y7, 700f, 200f,
            "G6",
            Paint().apply { color = Color.parseColor("#9AC7BF")},
            galleryTextPaint)
        museum.addGallery(gallery7)

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

        // Obtén el tamaño del View
        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()

        // Define el tamaño del contenido original
        val originalWidth = 1100f
        val originalHeight = 2300f

        // Calcula la escala para ajustar el contenido al View
        val scaleX = viewWidth / originalWidth
        val scaleY = viewHeight / originalHeight

        // Aplica la escala adicional
        val finalScaleX = scaleX
        val finalScaleY = scaleY

        val finalScale = if (finalScaleX < finalScaleY) finalScaleX else finalScaleY

        // Crea una matriz para la transformación
        val matrix = Matrix()
        matrix.setScale(finalScale, finalScale)

        // Aplica la matriz al Canvas
        canvas.setMatrix(matrix)
//        canvas.scale(scale, scale, canvas.width / 2f, canvas.height / 2f)
//        canvas.translate(canvasTranslateX, canvasTranslateY)
        Log.d(TAG, "onDraw: scale: $scale, translateX: $canvasTranslateX, translateY: $canvasTranslateY")
        museum.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d(TAG, "x: ${event.x}, y: ${event.y}")
        Log.i(TAG, "scale: $scale, translationX: $canvasTranslateX, translationY: $canvasTranslateY")
        val scaledX = (event.x - canvasTranslateX) / scale
        val scaledY = (event.y - canvasTranslateY) / scale


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