package com.quantumsoft.myapplication.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AutorDao {
    @Insert
    suspend fun insertAutor(autor: Autor)

    @Query("SELECT * FROM autores")
    suspend fun getAllAutores(): List<Autor>
}
