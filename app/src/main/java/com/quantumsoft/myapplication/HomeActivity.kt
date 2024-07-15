package com.quantumsoft.myapplication

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
import com.quantumsoft.myapplication.fragments.CuadrosFragment
import com.quantumsoft.myapplication.fragments.QRFragment
import com.quantumsoft.myapplication.fragments.MapaFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.quantumsoft.myapplication.viewmodel.MuseoViewModel
import android.Manifest
class HomeActivity : AppCompatActivity() {

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
        /*
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val textViewWelcome: TextView = binding.textViewWelcome
        val username = intent.getStringExtra("username")
        textViewWelcome.text = "Bienvenido $username"

        val textViewName: TextView = findViewById(R.id.textViewName)
        val textViewEmail: TextView = findViewById(R.id.textViewEmail)

        val accountEntityJson = intent.getStringExtra("ACCOUNT")
        //el json que recibo lo convierto en un objeto otra vez
        val gson = Gson()
        val accountEntity = gson.fromJson(accountEntityJson, AccountEntity::class.java)

        textViewName.text = accountEntity.firstName
        textViewEmail.text = accountEntity.email

        Log.d("Home Activity", accountEntityJson)
        */

        fragmentManager = supportFragmentManager
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
                    cuadrosFragment = CuadrosFragment.newInstance("", "",
                        MuseoViewModel(applicationContext),
                        object:FragmentChanger{
                        override fun changeFragment(fragment: Fragment?) {
                            loadFragment(fragment)
                        }
                    })
                    loadFragment(cuadrosFragment)
                    true
                }
                R.id.menu_mapa -> {
                    mapaFragment = MapaFragment.newInstance("", "")
                    loadFragment(mapaFragment)
                    true
                }
                else -> false
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
}
