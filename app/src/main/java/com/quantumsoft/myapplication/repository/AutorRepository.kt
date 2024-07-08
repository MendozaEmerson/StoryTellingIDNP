package com.quantumsoft.myapplication.repository

import android.content.Context
import com.quantumsoft.myapplication.model.AppDatabase
import com.quantumsoft.myapplication.model.data.Autor
import com.quantumsoft.myapplication.model.data.AutorDao


class AutorRepository(context: Context) {
    private val autorDao: AutorDao = AppDatabase.getDatabase(context).autorDao()
    suspend fun insertAutor(autor: Autor) = autorDao.insertAutor(autor)
    suspend fun getAllAutores() = autorDao.getAllAutores()

    suspend fun getAutorById(autorId: Int) = autorDao.getAutorById(autorId)
}
