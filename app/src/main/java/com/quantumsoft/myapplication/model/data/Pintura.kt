package com.quantumsoft.myapplication.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "pinturas",
    foreignKeys = [
        ForeignKey(
            entity = Sala::class,
            parentColumns = ["id"],
            childColumns = ["salaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Autor::class,
            parentColumns = ["id"],
            childColumns = ["autorId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Pintura(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val salaId: Int,
    val titulo: String,
    val autorId: Int,
    val tecnica: String,
    val categoria: String,
    val descripcion: String,
    val ano: Int,
    val enlace: String,
    val posX: Float,
    val posY: Float,
    val width: Float,
    val height: Float
)
