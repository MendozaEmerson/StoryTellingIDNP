package com.quantumsoft.myapplication

import android.os.Bundle
//import androidx.activity.EdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

class HomeActivity : AppCompatActivity() {

    //private lateinit var binding: ActivityHomeBinding
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null

//    private var qrFragment: QRFragment? = null
    private var insertDataFragment: InsertDataFragment? = null
    private var cuadrosFragment: CuadrosFragment? = null
    private var mapaFragment: MapaFragment? = null

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
                    cuadrosFragment = CuadrosFragment.newInstance("", "",   object:FragmentChanger{
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
