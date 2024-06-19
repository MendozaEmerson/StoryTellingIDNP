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
        val galeria = Galeria(0f, 0f, 480f, 300f, Color.GREEN)

        // Agregar figuras ajustadas a la galería
        val esculturaInGallery = Escultura(80f, 0f, 40f, Color.MAGENTA)
        galeria.addDrawableShape(esculturaInGallery)

        val pinturaInGallery = Pintura(10f, 10f, 90f, 90f, Color.YELLOW)
        // Agregar rectángulo con un desplazamiento
        galeria.addDrawableShape(pinturaInGallery, 0f, 0f)

        val pinturaInGallery1 = Pintura(50f, 50f, 90f, 90f, Color.BLUE)
        // Agregar rectángulo con un desplazamiento
        galeria.addDrawableShape(pinturaInGallery1)


        // Agregar la galería al CanvasMap
        canvasMap.addDrawableShape(galeria)
    }
}