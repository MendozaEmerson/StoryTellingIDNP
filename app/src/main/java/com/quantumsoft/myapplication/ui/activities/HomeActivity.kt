package com.quantumsoft.myapplication.ui.activities

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
//import androidx.activity.EdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
//import com.quantumsoft.myapplication.databinding.ActivityHomeBinding
import com.quantumsoft.myapplication.ui.fragments.CuadrosFragment
import com.quantumsoft.myapplication.ui.fragments.MapaFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.quantumsoft.myapplication.viewmodel.MuseoViewModel
import android.Manifest
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quantumsoft.myapplication.viewmodel.InsertDataFragment
import com.quantumsoft.myapplication.R
import com.quantumsoft.myapplication.data.local.AppDatabase
import com.quantumsoft.myapplication.data.local.entities.Exposicion
import com.quantumsoft.myapplication.data.repository.AutorRepository
import com.quantumsoft.myapplication.data.repository.ExposicionRepository
import com.quantumsoft.myapplication.data.repository.PinturaRepository
import com.quantumsoft.myapplication.data.repository.SalaRepository
import com.quantumsoft.myapplication.services.AudioPlayServices
import com.quantumsoft.myapplication.ui.fragments.AudioFragment
import com.quantumsoft.myapplication.viewmodel.FragmentChanger
import com.quantumsoft.myapplication.viewmodel.MuseoViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


// data clas for:
//{
//    "titulo": "titulo",
//    "autorId": "gold",
//    "tecnica": "tecnica 1",
//    "categoria": "categoria 1",
//    "descripcion": "holaaaaaaa",
//    "enlace": "https://example.com",
//    "ano": 2024,
//    "posX": 10.0,
//    "posY": 20.0,
//    "width": 40.0,
//    "height": 10.0
//},
//data class Pintu(
//    val titulo: String,
//    val autorId: String,
//    val tecnica: String,
//    val categoria: String,
//    val descripcion: String,
//    val enlace: String,
//    val ano: Int,
//    val posX: Float,
//    val posY: Float,
//    val width: Float,
//    val height: Float
//)

class HomeActivity : AppCompatActivity(), FragmentChanger {

    private lateinit var museoViewModel: MuseoViewModel


    //private lateinit var binding: ActivityHomeBinding
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null

//    private var qrFragment: QRFragment? = null
    private var insertDataFragment: InsertDataFragment? = null
    private var cuadrosFragment: CuadrosFragment? = null
    private var mapaFragment: MapaFragment? = null
    private fun showPermissionStatus(message: String) {
        // Aquí puedes mostrar un mensaje al usuario, por ejemplo usando un Toast o un Snackbar
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private suspend fun downloadJsonIfNotExists(context: Context) {
        withContext(Dispatchers.IO) {
            val fileName = "data5.json"
            val file = File(context.filesDir, fileName)

            // Verifica si el archivo ya existe
//            if (!file.exists()) {
                val jsonUrl = "https://frank-c0.github.io/museo-virtual-app/static/pinturas.json" // Cambia por la URL real
                var jsonString = ""

                try {
                    val url = URL(jsonUrl)
                    val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.connect()

                    if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                        val inputStream = urlConnection.inputStream
                        val reader = BufferedReader(InputStreamReader(inputStream))
                        jsonString = reader.use { it.readText() }
                    } else {
                        Log.e("DownloadError", "Server responded with code: ${urlConnection.responseCode}")
                    }

                    // Guarda el JSON en un archivo
                    FileOutputStream(file).use { outputStream ->
                        outputStream.write(jsonString.toByteArray())
                    }

                    // Inicializa la base de datos
                    val gson = Gson()
                    val type = object : TypeToken<List<Exposicion>>() {}.type
                    val dataList: List<Exposicion> = gson.fromJson(jsonString, type)

                    Log.d("DownloadedData", "dddddddddddddddddd")
                    for (pintura in dataList) {
                        Log.d("DownloadedData", pintura.titulo)
                    }

                    val db = AppDatabase.getDatabase(context)
                    db.exposicionDao().insertExposiciones(dataList)


                    Log.d("DownloadedData", "dddddddddddddddddd2")
                    // log all exposiciones records in the database
                    val exposiciones = db.exposicionDao().getAllExposiciones()
                    for (exposicion in exposiciones) {
                        Log.d("Exposicion", exposicion.titulo)
                    }

                } catch (e: Exception) {
                    Log.e("DownloadError", "Error downloading or saving JSON", e)
                }
//            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //EdgeToEdge.enable(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("HomeActivity", "onCreate")
        // Llama a la función en un coroutine utilizando el contexto de IO
        CoroutineScope(Dispatchers.Main).launch {
            downloadJsonIfNotExists(applicationContext)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                    if (isGranted) {
                        showPermissionStatus("Permiso de notificaciones concedido.")
                    } else {
                        showPermissionStatus("Permiso de notificaciones denegado.")
                    }
                }

                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                showPermissionStatus("Permiso de notificaciones concedido.")
            }
        } else {
            showPermissionStatus("No se requiere permiso para notificaciones en esta versión de Android.")
        }

        // Inicializa tus repositorios
        val pinturaRepository = PinturaRepository(applicationContext) // O pasa el contexto de forma segura
        val salaRepository = SalaRepository(applicationContext)
        val autorRepository = AutorRepository(applicationContext)
        val exposicionRepository = ExposicionRepository(applicationContext)
        // Crea el ViewModel usando el factory
//        val factory = MuseoViewModelFactory(pinturaRepository, salaRepository, autorRepository)
        val factory = MuseoViewModelFactory(exposicionRepository)
        museoViewModel = ViewModelProvider(this, factory).get(MuseoViewModel::class.java)

        fragmentManager = supportFragmentManager

//      inflate audio fragment in to fragmentContainerAudio (androidx.fragment.app.FragmentContainerView)
        // Solo añade el Fragment si no hay estado guardado
        if (savedInstanceState == null) {
            val audioFragment = AudioFragment.newInstance("", "")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerAudio, audioFragment)
                .commit()
        }


        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.menu_home
        bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    insertDataFragment = InsertDataFragment.newInstance("", "")
                    loadFragment(insertDataFragment)
                    true
                }
                R.id.menu_cuadros -> {
                    cuadrosFragment = CuadrosFragment.newInstance("", "")
                    loadFragment(cuadrosFragment)
                    true
                }
                R.id.menu_mapa -> {
                    mapaFragment = MapaFragment.newInstance("", "")
                    loadFragment(mapaFragment)
                    true
                }
                else -> {
                    false
                }
            }
        })
    }

    private fun loadFragment(fragment: Fragment?) {
        if (fragmentManager != null) {
            fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction!!.replace(R.id.fragmentContainerView, fragment!!)
            fragmentTransaction!!.commit()
        }
    }

    override fun changeFragment(fragment: Fragment?) {
        loadFragment(fragment)
    }


    override fun onPause() {
        super.onPause()
        val intent = Intent(this, AudioPlayServices::class.java).apply {
            putExtra(AudioPlayServices.COMMAND, AudioPlayServices.SHOW_NOTIFICATION)
        }
        startService(intent) // Muestra la notificación
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, AudioPlayServices::class.java).apply {
            putExtra(AudioPlayServices.COMMAND, AudioPlayServices.HIDE_NOTIFICATION)
        }
        startService(intent) // Oculta la notificación
    }
}
