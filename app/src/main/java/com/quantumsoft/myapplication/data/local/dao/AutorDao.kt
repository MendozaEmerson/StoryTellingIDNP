package com.quantumsoft.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.quantumsoft.myapplication.data.local.entities.Autor

@Dao
interface AutorDao {
    @Query("SELECT * FROM autores WHERE id = :autorId")
    suspend fun getAutorById(autorId: Int): Autor

    @Insert
    suspend fun insertAutor(autor: Autor)

    @Query("SELECT * FROM autores")
    suspend fun getAllAutores(): List<Autor>
}
