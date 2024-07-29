package com.quantumsoft.myapplication.data.repository

import android.content.Context
import com.quantumsoft.myapplication.data.local.AppDatabase
import com.quantumsoft.myapplication.data.local.entities.Sala
import com.quantumsoft.myapplication.data.local.dao.SalaDao

class SalaRepository(context: Context) {
    private val salaDao: SalaDao = AppDatabase.getDatabase(context).salaDao()
    suspend fun insertSala(sala: Sala) = salaDao.insertSala(sala)
    suspend fun getAllSalas() = salaDao.getAllSalas()

    suspend fun getSalaById(salaId: Int) = salaDao.getSalaById(salaId)
}