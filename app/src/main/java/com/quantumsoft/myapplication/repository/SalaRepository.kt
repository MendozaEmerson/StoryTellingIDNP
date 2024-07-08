package com.quantumsoft.myapplication.repository

import android.content.Context
import com.quantumsoft.myapplication.model.AppDatabase
import com.quantumsoft.myapplication.model.data.Sala
import com.quantumsoft.myapplication.model.data.SalaDao

class SalaRepository(context: Context) {
    private val salaDao: SalaDao = AppDatabase.getDatabase(context).salaDao()
    suspend fun insertSala(sala: Sala) = salaDao.insertSala(sala)
    suspend fun getAllSalas() = salaDao.getAllSalas()

    suspend fun getSalaById(salaId: Int) = salaDao.getSalaById(salaId)
}