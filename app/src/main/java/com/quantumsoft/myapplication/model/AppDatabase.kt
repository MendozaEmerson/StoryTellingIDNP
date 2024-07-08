package com.quantumsoft.myapplication.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.quantumsoft.myapplication.model.data.Autor
import com.quantumsoft.myapplication.model.data.AutorDao
import com.quantumsoft.myapplication.model.data.Pintura
import com.quantumsoft.myapplication.model.data.PinturaDao
import com.quantumsoft.myapplication.model.data.Sala
import com.quantumsoft.myapplication.model.data.SalaDao

@Database(entities = [Sala::class, Pintura::class, Autor::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun salaDao(): SalaDao
    abstract fun pinturaDao(): PinturaDao
    abstract fun autorDao(): AutorDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}