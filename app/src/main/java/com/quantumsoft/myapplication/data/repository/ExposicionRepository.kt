package com.quantumsoft.myapplication.data.repository

import android.content.Context
import com.quantumsoft.myapplication.data.local.AppDatabase
import com.quantumsoft.myapplication.data.local.dao.ExposicionDao
import com.quantumsoft.myapplication.data.local.entities.Exposicion

class ExposicionRepository(context: Context) {
    private val exposicionDao: ExposicionDao = AppDatabase.getDatabase(context).exposicionDao()

    suspend fun insertExposicion(exposicion: Exposicion) = exposicionDao.insertExposicion(exposicion)
    suspend fun getAllExposiciones() = exposicionDao.getAllExposiciones()
    suspend fun getExposicionById(exposicionId: Long) = exposicionDao.getExposicionById(exposicionId)
}