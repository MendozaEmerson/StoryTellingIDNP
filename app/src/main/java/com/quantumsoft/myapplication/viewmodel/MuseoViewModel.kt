package com.quantumsoft.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.quantumsoft.myapplication.AdapterRecyclerView
import com.quantumsoft.myapplication.model.data.Autor
import com.quantumsoft.myapplication.model.data.Pintura
import com.quantumsoft.myapplication.model.data.Sala
import com.quantumsoft.myapplication.repository.AutorRepository
import com.quantumsoft.myapplication.repository.PinturaRepository
import com.quantumsoft.myapplication.repository.SalaRepository

import kotlinx.coroutines.launch

data class AutorConPinturas(
    val autor: Autor,
    val pinturas: List<Pintura>
)

data class SalaConPinturas(
    val sala: Sala,
    val pinturas: List<Pintura>
)
class MuseoViewModel(context: Context) : ViewModel() {

    private val pinturaRepository = PinturaRepository(context)
    private val salaRepository = SalaRepository(context)
    private val autorRepository = AutorRepository(context)

    private val _pinturas = MutableLiveData<List<Pintura>>()
    val pinturas: LiveData<List<Pintura>> = _pinturas

    private val _items = MutableLiveData<List<AdapterRecyclerView.Item>>()
    val items: LiveData<List<AdapterRecyclerView.Item>> = _items

    fun updateListItems() {
        viewModelScope.launch {
            val items: MutableList<AdapterRecyclerView.Item> = mutableListOf()
            val pinturas = pinturaRepository.getAllPinturas()
            for (pintura in pinturas) {
                items.add(
                    AdapterRecyclerView.Item(
                        imageResId= 1,
                        title= pintura.titulo,
                        location= salaRepository.getSalaById(pintura.salaId).nombre,
                        author= autorRepository.getAutorById(pintura.autorId).nombre,
                    )
                )
            }
            _items.value = items
        }
    }

    fun updateListPinturas() {
        viewModelScope.launch {
            _pinturas.value = pinturaRepository.getAllPinturas()
        }
    }


    fun getPinturasBySalaId(salaId: Int) {
        viewModelScope.launch {
            _pinturas.value = pinturaRepository.getPinturasBySalaId(salaId)
        }
    }

    fun getPinturasByAutorId(autorId: Int) {
        viewModelScope.launch {
            _pinturas.value = pinturaRepository.getPinturasByAutorId(autorId)
        }
    }

//    get autor name by id
    fun getAutorById(autorId: Int): String {
        var autorName = ""
        viewModelScope.launch {
            autorName = autorRepository.getAutorById(autorId).nombre
        }
        return autorName
    }
//    get sala name by id
    fun getSalaById(salaId: Int): String {
        var salaName = ""
        viewModelScope.launch {
            salaName = salaRepository.getSalaById(salaId).nombre
        }
        return salaName
    }

    fun getPinturasByAutorIdWithAutor(autorId: Int): LiveData<AutorConPinturas> {
        val result = MutableLiveData<AutorConPinturas>()
        viewModelScope.launch {
            val autor = autorRepository.getAutorById(autorId)
            val pinturas = pinturaRepository.getPinturasByAutorId(autorId)
            result.value = AutorConPinturas(autor, pinturas)
        }
        return result
    }

    fun getPinturasBySalaIdWithSala(salaId: Int): LiveData<SalaConPinturas> {
        val result = MutableLiveData<SalaConPinturas>()
        viewModelScope.launch {
            val sala = salaRepository.getSalaById(salaId)
            val pinturas = pinturaRepository.getPinturasBySalaId(salaId)
            result.value = SalaConPinturas(sala, pinturas)
        }
        return result
    }

//    add example sala, autor and pintura
    fun addExampleData() {
        viewModelScope.launch {

//            val sala = Sala(0, "Sala 1", "Descripcion Sala 1", 0.0f, 0.0f, 0.0f, 0.0f)
            val sala = Sala(
                nombre = "Sala 1",
                descripcion = "Descripcion Sala 1",
                posX = 0.0f,
                posY = 0.0f,
                width = 0.0f,
                height = 0.0f
            )


            salaRepository.insertSala(sala)


            val autor = Autor(
                nombre = "Autor 1",
                apellido = "Apellido 1",
            )
            autorRepository.insertAutor(autor)

            val pintura = Pintura(
                salaId = 1,
                titulo = "Pintura 1",
                autorId = 1,
                tecnica = "Tecnica 1",
                categoria = "Categoria 1",
                descripcion = "Descripcion 1",
                ano = 2021,
                enlace = "https://www.example.com/imagen.jpg",
                posX = 0.0f,
                posY = 0.0f,
                width = 0.0f,
                height = 0.0f
            )


            pinturaRepository.insertPintura(pintura)
        }
    }



}