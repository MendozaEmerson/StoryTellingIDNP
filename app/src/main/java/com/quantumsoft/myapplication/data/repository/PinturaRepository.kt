package com.quantumsoft.myapplication.data.repository

import android.content.Context
import com.quantumsoft.myapplication.data.local.AppDatabase
import com.quantumsoft.myapplication.data.local.entities.Pintura
import com.quantumsoft.myapplication.data.local.dao.PinturaDao

class PinturaRepository(context: Context) {
    private val pinturaDao: PinturaDao = AppDatabase.getDatabase(context).pinturaDao()

//    suspend fun getPinturaById(pinturaId: Int) = pinturaDao.getPinturaById(pinturaId)
//
//    suspend fun insertPintura(pintura: Pintura) = pinturaDao.insertPintura(pintura)
//    suspend fun getAllPinturas() = pinturaDao.getAllPinturas()
//
//
//    suspend fun getPinturasBySalaId(salaId: Int): List<Pintura> = pinturaDao.getPinturasBySalaId(salaId)
//
//    suspend fun getPinturasByAutorId(autorId: Int): List<Pintura> = pinturaDao.getPinturasByAutorId(autorId)
//
//    suspend fun getSalaById(salaId: Int) = pinturaDao.getSalaById(salaId)
//
//    suspend fun getAutorById(autorId: Int) = pinturaDao.getAutorById(autorId)
}
