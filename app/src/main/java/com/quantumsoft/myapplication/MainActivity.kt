package com.quantumsoft.myapplication

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var canvasMap: CanvasMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        canvasMap = findViewById(R.id.canvasMap)

        // Crear una galería como un rectángulo dentro de los límites del CanvasMap
        val galeria1 = Galeria(50f, 50f, 300f, 300f, Color.GREEN, "Galeria 1")

        // Agregar figuras ajustadas a la galería
        val esculturaInGallery = Escultura(0f, 0f, 20f, Color.MAGENTA)
        galeria1.addDrawableShape(esculturaInGallery)

        // Obtener la coordenada global de la esquina inferior izquierda de galeria1
        val bottomLeftOfGallery1 = galeria1.getBottomLeft()

        // Crear una nueva galería justo debajo de gallery1
        //val offsetX = 0f
        val offsetY = 0f // Un pequeño margen entre las galerías
        val gallery2Top = bottomLeftOfGallery1.second + offsetY
        val gallery2 = Galeria(
            left = bottomLeftOfGallery1.first,
            top = gallery2Top,
            right = bottomLeftOfGallery1.first + (galeria1.getTopRight().first - galeria1.getTopLeft().first),
            bottom = gallery2Top + (galeria1.getBottomRight().second - galeria1.getTopLeft().second),
            borderColor = Color.BLUE,
            "Galeria 4"
        )
        // Agregar la galería al CanvasMap
        canvasMap.addDrawableShape(galeria1)

        canvasMap.addDrawableShape(gallery2)
    }
}