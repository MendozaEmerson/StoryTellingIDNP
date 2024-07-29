package com.quantumsoft.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quantumsoft.myapplication.ui.adapters.AdapterRecyclerView
import com.quantumsoft.myapplication.data.local.entities.Autor
import com.quantumsoft.myapplication.data.local.entities.Pintura
import com.quantumsoft.myapplication.data.local.entities.Sala
import com.quantumsoft.myapplication.data.repository.AutorRepository
import com.quantumsoft.myapplication.data.repository.PinturaRepository
import com.quantumsoft.myapplication.data.repository.SalaRepository

import kotlinx.coroutines.launch

class MuseoViewModel(context: Context) : ViewModel() {

    private val pinturaRepository = PinturaRepository(context)
    private val salaRepository = SalaRepository(context)
    private val autorRepository = AutorRepository(context)

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
                        title=pintura.titulo,
                        location= salaRepository.getSalaById(pintura.salaId).nombre,
                        author= autorRepository.getAutorById(pintura.autorId).nombre,
                    )
                )
            }
            _items.value = items
        }
    }

    fun addExampleData() {
        viewModelScope.launch {
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