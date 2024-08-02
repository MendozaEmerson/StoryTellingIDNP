package com.quantumsoft.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.quantumsoft.myapplication.data.local.entities.Autor
import com.quantumsoft.myapplication.data.local.entities.Pintura
import com.quantumsoft.myapplication.data.local.entities.Sala

@Dao
interface PinturaDao {

//

    @Query("SELECT * FROM pinturas WHERE id = :pinturaId")
    suspend fun getPinturaById(pinturaId: Int): Pintura

    @Insert
    suspend fun insertPintura(pintura: Pintura)

    @Query("SELECT * FROM pinturas")
    suspend fun getAllPinturas(): List<Pintura>



    @Query("SELECT * FROM salas WHERE id = :salaId")
    suspend fun getSalaById(salaId: Int): Sala

    @Query("""
        SELECT pinturas.*
        FROM pinturas
        INNER JOIN salas ON pinturas.salaId = salas.id
        WHERE pinturas.salaId = :salaId
    """)
    suspend fun getPinturasBySalaId(salaId: Int): List<Pintura>

    @Query("SELECT * FROM autores WHERE id = :autorId")
    suspend fun getAutorById(autorId: Int): Autor

    @Query("""
        SELECT pinturas.*
        FROM pinturas
        INNER JOIN autores ON pinturas.autorId = autores.id
        WHERE pinturas.autorId = :autorId
    """)
    suspend fun getPinturasByAutorId(autorId: Int): List<Pintura>
}
