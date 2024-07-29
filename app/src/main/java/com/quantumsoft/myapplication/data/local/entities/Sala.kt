package com.quantumsoft.myapplication.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "salas")
data class Sala(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val posX: Float,
    val posY: Float,
    val width: Float,
    val height: Float
)