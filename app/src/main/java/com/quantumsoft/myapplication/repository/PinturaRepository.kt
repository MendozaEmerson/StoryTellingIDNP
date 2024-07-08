package com.quantumsoft.myapplication.repository

import com.quantumsoft.myapplication.model.data.Pintura
import com.quantumsoft.myapplication.model.data.PinturaDao

class PinturaRepository(private val pinturaDao: PinturaDao) {
    suspend fun insertPintura(pintura: Pintura) = pinturaDao.insertPintura(pintura)
    suspend fun getAllPinturas() = pinturaDao.getAllPinturas()
}
