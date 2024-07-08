package com.quantumsoft.myapplication.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SalaDao {
    @Insert
    suspend fun insertSala(sala: Sala)

    @Query("SELECT * FROM salas")
    suspend fun getAllSalas(): List<Sala>

    @Query("SELECT * FROM salas WHERE id = :salaId")
    suspend fun getSalaById(salaId: Int): Sala
}
