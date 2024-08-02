package com.quantumsoft.myapplication.data.repository

import android.content.Context
import com.quantumsoft.myapplication.data.local.AppDatabase
import com.quantumsoft.myapplication.data.local.entities.Autor
import com.quantumsoft.myapplication.data.local.dao.AutorDao


class AutorRepository(context: Context) {
    private val autorDao: AutorDao = AppDatabase.getDatabase(context).autorDao()
//    suspend fun insertAutor(autor: Autor) = autorDao.insertAutor(autor)
//    suspend fun getAllAutores() = autorDao.getAllAutores()
//
//    suspend fun getAutorById(autorId: Int) = autorDao.getAutorById(autorId)
}
