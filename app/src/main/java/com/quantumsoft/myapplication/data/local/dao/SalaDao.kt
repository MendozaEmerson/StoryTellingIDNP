package com.quantumsoft.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.quantumsoft.myapplication.data.local.entities.Sala

@Dao
interface SalaDao {
    @Insert
    suspend fun insertSala(sala: Sala)

    @Query("SELECT * FROM salas")
    suspend fun getAllSalas(): List<Sala>

    @Query("SELECT * FROM salas WHERE id = :salaId")
    suspend fun getSalaById(salaId: Int): Sala
}
