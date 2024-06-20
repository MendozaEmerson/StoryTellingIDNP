package com.quantumsoft.myapplication.Canvas

import android.graphics.Canvas

class Museum {

    val galleries = mutableListOf<Gallery>()

    fun addGallery(gallery: Gallery) {
        galleries.add(gallery)
    }

    fun draw(canvas: Canvas) {

        for (gallery in galleries) {
            gallery.draw(canvas)
        }

    }
}