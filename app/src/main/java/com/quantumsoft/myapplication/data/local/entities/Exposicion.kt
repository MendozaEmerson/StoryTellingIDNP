package com.quantumsoft.myapplication.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


//"titulo": "Una taza de té",
//"autor": "Isabella Ríos",
//"tecnica": "Óleo sobre lienzo",
//"sala": "Sala 1",
//"descripcion": "Esta obra presenta una escena encantadora en la que una llama, sumida en un estado de tranquilidad y comodidad, disfruta plácidamente de una taza de té. La atmósfera refleja una tarde fría y melancólica, con un entorno sereno que acentúa la paz del momento.",
//"imagen_link": "https://github.com/Jeffrey-7101/Static-museo-virtual/blob/main/Imagenes/Fotos%20editadas/IMG_20240719_142844(1).png?raw=true",
//"audio_link": "https://github.com/Jeffrey-7101/Static-museo-virtual/blob/main/Audios/Una%20taza%20de%20te.mp3"

@Entity(tableName = "exposiciones")
data class Exposicion(
//    non null manual id
    @PrimaryKey val id: Long = 0,
    val titulo: String,
    val autor: String,
    val tecnica: String,
    val sala: String,
    val descripcion: String,
    val imagen_link: String,
    val audio_link: String
)
