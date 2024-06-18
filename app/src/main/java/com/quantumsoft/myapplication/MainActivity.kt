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
        val gallery = Gallery(50f, 50f, 380f, 200f, Color.GREEN)

        // Agregar figuras ajustadas a la galería
        val circleInGallery = Circle(0f, 0f, 40f, Color.MAGENTA)
        gallery.addDrawableShape(circleInGallery)

        val rectangleInGallery = Rectangle(50f, 50f, 100f, 100f, Color.YELLOW)
        // Agregar rectángulo con un desplazamiento
        gallery.addDrawableShape(rectangleInGallery)

        val rectangleInGallery1 = Rectangle(50f, 50f, 90f, 90f, Color.BLUE)
        // Agregar rectángulo con un desplazamiento
        gallery.addDrawableShape(rectangleInGallery1)


        // Agregar la galería al CanvasMap
        canvasMap.addDrawableShape(gallery)
    }
}