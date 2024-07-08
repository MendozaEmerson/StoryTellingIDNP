package com.quantumsoft.myapplication.repository

import com.quantumsoft.myapplication.model.data.Autor
import com.quantumsoft.myapplication.model.data.AutorDao


class AutorRepository(private val autorDao: AutorDao) {
    suspend fun insertAutor(autor: Autor) = autorDao.insertAutor(autor)
    suspend fun getAllAutores() = autorDao.getAllAutores()
}
