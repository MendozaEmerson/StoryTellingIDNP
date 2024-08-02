package com.quantumsoft.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.quantumsoft.myapplication.data.local.entities.Autor
import com.quantumsoft.myapplication.data.local.entities.Exposicion

@Dao
interface ExposicionDao {
    @Query("SELECT * FROM exposiciones WHERE id = :exposicionId")
    suspend fun getExposicionById(exposicionId: Long): Exposicion

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExposicion(exposicion: Exposicion)

    @Query("SELECT * FROM exposiciones")
    suspend fun getAllExposiciones(): List<Exposicion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExposiciones(exposiciones: List<Exposicion>)

    @Query("SELECT * FROM exposiciones WHERE titulo LIKE :query OR autor LIKE :query OR tecnica LIKE :query OR sala LIKE :query OR descripcion LIKE :query")
    suspend fun searchExposiciones(query: String): List<Exposicion>


}
