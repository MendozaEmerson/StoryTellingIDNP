package com.quantumsoft.myapplication


import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun downloadJsonIfNotExists(context: Context) {
    val fileName = "data.json"
    val file = File(context.filesDir, fileName)

    // Verifica si el archivo ya existe
    if (!file.exists()) {
        val jsonUrl = "https://tu-base-url.com/ruta/al/archivo.json" // Cambia por la URL real
        var jsonString = ""

        val url = URL(jsonUrl)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

        try {
            urlConnection.requestMethod = "GET"
            urlConnection.connect()

            if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = urlConnection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                jsonString = reader.use { it.readText() }
                reader.close()
            }

            // Guarda el JSON en un archivo
            FileOutputStream(file).use { outputStream ->
                outputStream.write(jsonString.toByteArray())
            }

//            // Inicializa la base de datos
//            val gson = Gson()
//            val type = object : TypeToken<List<YourDataClass>>() {}.type
//            val dataList: List<YourDataClass> = gson.fromJson(jsonString, type)
//
//            val db = AppDatabase.getDatabase(context)
//            db.yourDao().insertAll(dataList)

        } catch (e: Exception) {
            Log.e("DownloadError", "Error downloading or saving JSON", e)
        } finally {
            urlConnection.disconnect()
        }
    }
}


data class test(val a: Int, val b: Int) {
    fun sum(): Int {
        return a + b
    }
}


fun main() {
    val test = test(1, 2)
    println(test.sum())
}
