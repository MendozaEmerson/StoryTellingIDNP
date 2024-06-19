package com.quantumsoft.myapplication

import android.graphics.Canvas

class Museum {

    val galleries = mutableListOf<Gallery>()

    // Parámetros de recorte y escalado
    private var clipX: Float = 0f
    private var clipY: Float = 0f
    private var clipWidth: Float = 1000f
    private var clipHeight: Float = 1000f

    fun addGallery(gallery: Gallery) {
        galleries.add(gallery)
    }

    // Configurar área de recorte
//    fun setClipArea(x: Float, y: Float, width: Float, height: Float) {
//        clipX = x
//        clipY = y
//        clipWidth = width
//        clipHeight = height
//    }

    // Dibuja sólo lo que está dentro del área de recorte
    fun draw(canvas: Canvas) {
        // Guardar estado del canvas
//        val saveCount = canvas.save()
//
//        // Calcular escala manteniendo el aspecto
//        val aspectRatio = clipWidth / clipHeight
//        val canvasWidth = canvas.width.toFloat()
//        val canvasHeight = canvas.height.toFloat()
//        val canvasAspectRatio = canvasWidth / canvasHeight
//        val scale: Float
//        val translateX: Float
//        val translateY: Float
//
//        if (aspectRatio > canvasAspectRatio) {
//            scale = canvasWidth / clipWidth
//            translateX = 0f
//            translateY = (canvasHeight - (clipHeight * scale)) / 2f
//        } else {
//            scale = canvasHeight / clipHeight
//            translateX = (canvasWidth - (clipWidth * scale)) / 2f
//            translateY = 0f
//        }
//
//        // Aplicar transformación de escala y translación
//        canvas.translate(translateX, translateY)
//        canvas.scale(scale, scale)
//        canvas.translate(-clipX, -clipY)
//
//        // Aplicar el recorte
//        canvas.clipRect(clipX, clipY, clipX + clipWidth, clipY + clipHeight)

        // Dibujar galerías visibles
        for (gallery in galleries) {
            if (gallery.isVisibleInClip(clipX, clipY, clipWidth, clipHeight)) {
                gallery.draw(canvas)
            }
        }

        // Restaurar estado del canvas
//        canvas.restoreToCount(saveCount)
    }
}
