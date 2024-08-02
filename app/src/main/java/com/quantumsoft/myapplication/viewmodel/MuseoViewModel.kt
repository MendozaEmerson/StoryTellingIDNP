package com.quantumsoft.myapplication.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.quantumsoft.myapplication.data.local.entities.Exposicion
import com.quantumsoft.myapplication.ui.adapters.AdapterRecyclerView
import com.quantumsoft.myapplication.data.local.entities.Pintura
import com.quantumsoft.myapplication.data.repository.ExposicionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MuseoViewModel(
//    private val pinturaRepository: PinturaRepository,
//    private val salaRepository: SalaRepository,
//    private val autorRepository: AutorRepository,
    private val exposicionRepository: ExposicionRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<AdapterRecyclerView.Item>>()
    val items: LiveData<List<AdapterRecyclerView.Item>> = _items

    fun updateListItems() {
        viewModelScope.launch {
            try {
                val items: MutableList<AdapterRecyclerView.Item> = mutableListOf()
                val pinturas = exposicionRepository.getAllExposiciones()
                for (pintura in pinturas) {
                    items.add(
                        AdapterRecyclerView.Item(
                            id = pintura.id,
                            imageResId = 1, // Considera obtener la ID de la imagen correctamente
                            title = pintura.titulo,
//                            location = salaRepository.getSalaById(pintura.salaId)?.nombre ?: "Desconocido",
//                            author = autorRepository.getAutorById(pintura.autorId)?.nombre ?: "Desconocido",

                            location = pintura.tecnica,
                            author = pintura.autor,
                            image_url = pintura.imagen_link
                        )
                    )
                }
                _items.value = items
            } catch (e: Exception) {
                // Maneja el error, por ejemplo, puedes registrar el error o mostrar un mensaje
            }
        }
    }

//    fun addExampleData() {
//        viewModelScope.launch {
//            try {
//                val sala = Sala(
//                    nombre = "Sala 1",
//                    descripcion = "Descripcion Sala 1",
//                    posX = 0.0f,
//                    posY = 0.0f,
//                    width = 0.0f,
//                    height = 0.0f
//                )
//                salaRepository.insertSala(sala)
//
//                val autor = Autor(
//                    nombre = "Autor 1",
//                    apellido = "Apellido 1",
//                )
//                autorRepository.insertAutor(autor)
//
//                val pintura = Pintura(
//                    salaId = 1,
//                    titulo = "Pintura 1",
//                    autorId = 1,
//                    tecnica = "Tecnica 1",
//                    categoria = "Categoria 1",
//                    descripcion = "Descripcion 1",
//                    ano = 2021,
//                    enlace = "https://www.example.com/imagen.jpg",
//                    posX = 0.0f,
//                    posY = 0.0f,
//                    width = 0.0f,
//                    height = 0.0f
//                )
//
//                pinturaRepository.insertPintura(pintura)
//            } catch (e: Exception) {
//                // Maneja el error
//            }
//        }
//    }

//    Cuadro detalle
    private val _pinturaActual = MutableLiveData<Exposicion>()
    val pinturaActual: LiveData<Exposicion> = _pinturaActual

    fun setPinturaActual(id: Long) {
        viewModelScope.launch {
            try {
                val pintura = exposicionRepository.getExposicionById(id)
                _pinturaActual.value = pintura
            } catch (e: Exception) {
                // Maneja el error
            }
        }
    }



//    Imagenes:
private val imageCache = mutableMapOf<String, Bitmap?>()

    fun getImage(url: String, callback: (Bitmap?) -> Unit) {
        // Verificar si la imagen ya está en caché
        imageCache[url]?.let {
            callback(it) // Devolver imagen de la caché
            return
        }

        // Si no está en caché, descargar la imagen
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = loadImageFromURL(url)
            if (bitmap != null) {
                imageCache[url] = bitmap // Almacenar en caché
            }
            withContext(Dispatchers.Main) {
                callback(bitmap) // Devolver la imagen descargada
            }
        }
    }

    private fun loadImageFromURL(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            bitmap = BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }


//    Current Exposicion playing audio
    private val _currentExposicionPLay = MutableLiveData<Exposicion?>()
    val currentExposicionPLay: LiveData<Exposicion?> = _currentExposicionPLay

    fun setExposicionPLay(id: Long?) {
        if (id == null) {
            _currentExposicionPLay.value = null
            return
        }
        viewModelScope.launch {
            try {
                val exposicion = exposicionRepository.getExposicionById(id)
                _currentExposicionPLay.value = exposicion
            } catch (e: Exception) {
                _currentExposicionPLay.value = null
            }
        }
    }



    private val audioCache = mutableMapOf<String, MediaPlayer?>()

    fun getAudio(url: String, callback: (MediaPlayer?) -> Unit) {
        // Verificar si el audio ya está en caché
        audioCache[url]?.let {
            callback(it) // Devolver audio de la caché
            return
        }

        // Si no está en caché, descargar el audio
        CoroutineScope(Dispatchers.IO).launch {
            val mediaPlayer = loadAudioFromURL(url)
            if (mediaPlayer != null) {
                audioCache[url] = mediaPlayer // Almacenar en caché
            }
            withContext(Dispatchers.Main) {
                callback(mediaPlayer) // Devolver el audio descargado
            }
        }
    }

    private fun loadAudioFromURL(url: String): MediaPlayer? {
        return try {
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(url) // Establecer la URL del audio
            mediaPlayer.prepare() // Preparar el MediaPlayer
            mediaPlayer // Devolver el MediaPlayer
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}

class MuseoViewModelFactory(
//    private val pinturaRepository: PinturaRepository,
//    private val salaRepository: SalaRepository,
//    private val autorRepository: AutorRepository,
    private val exposicionRepository: ExposicionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MuseoViewModel::class.java)) {
//            return MuseoViewModel(pinturaRepository, salaRepository, autorRepository) as T
            return MuseoViewModel(exposicionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
