package com.quantumsoft.myapplication.model.data

import androidx.room.Embedded
import androidx.room.Relation

data class PinturaWithDetails(
    @Embedded val pintura: Pintura,
    @Relation(
        parentColumn = "salaId",
        entityColumn = "id"
    )
    val sala: Sala,
    @Relation(
        parentColumn = "autorId",
        entityColumn = "id"
    )
    val autor: Autor
)