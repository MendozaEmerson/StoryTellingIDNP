package com.quantumsoft.myapplication.repository

import com.quantumsoft.myapplication.model.data.Sala
import com.quantumsoft.myapplication.model.data.SalaDao

class SalaRepository(private val salaDao: SalaDao) {
    suspend fun insertSala(sala: Sala) = salaDao.insertSala(sala)
    suspend fun getAllSalas() = salaDao.getAllSalas()
}