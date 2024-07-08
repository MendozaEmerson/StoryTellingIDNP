package com.quantumsoft.myapplication.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PinturaDao {
    @Insert
    suspend fun insertPintura(pintura: Pintura)

    @Query("SELECT * FROM pinturas")
    suspend fun getAllPinturas(): List<Pintura>
}
