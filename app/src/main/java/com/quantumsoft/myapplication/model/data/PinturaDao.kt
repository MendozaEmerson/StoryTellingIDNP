package com.quantumsoft.myapplication.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PinturaDao {
    @Insert
    suspend fun insertPintura(pintura: Pintura)

    @Query("SELECT * FROM pinturas")
    suspend fun getAllPinturas(): List<Pintura>

    @Transaction
    @Query("SELECT * FROM pinturas")
    suspend fun getPinturasWithDetails():List<PinturaWithDetails>
}
