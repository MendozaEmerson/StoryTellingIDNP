package com.example.loginsample2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.loginsample2.databinding.ActivityHomeBinding;
import com.example.loginsample2.fragments.CuadrosFragment;
import com.example.loginsample2.fragments.HomeFragment;
import com.example.loginsample2.fragments.MapaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;


public class HomeActivity extends AppCompatActivity{

    private ActivityHomeBinding binding;
    private FragmentManager fragmentManager=null;
    private FragmentTransaction fragmentTransaction=null;

    private HomeFragment homeFragment=null;
    private CuadrosFragment cuadrosFragment=null;
    private MapaFragment mapaFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       /* binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TextView textViewWelcome= binding.textViewWelcome;
        String username = getIntent().getStringExtra("username");
        textViewWelcome.setText("Bienvenido " + username);

        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewEmail = findViewById(R.id.textViewEmail);

        String accountEntityJson= getIntent().getStringExtra("ACCOUNT");
        //el json que recibo lo convierto en un objeto otra vez
        Gson gson= new Gson();
        AccountEntity accountEntity=gson.fromJson(accountEntityJson,AccountEntity.class);

        textViewName.setText(accountEntity.getFirstName());
        textViewEmail.setText(accountEntity.getEmail());

       Log.d("Home Activity", accountEntityJson);

        */

        fragmentManager=getSupportFragmentManager();
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.menu_home){
                    homeFragment=HomeFragment.newInstance("","");
                    loadFragment(homeFragment);
                    return true;
                }else if(menuItem.getItemId()==R.id.menu_cuadros){
                    cuadrosFragment=CuadrosFragment.newInstance("", "", new FragmentChanger() {
                        @Override
                        public void changeFragment(Fragment fragment) {
                            loadFragment(fragment);
                        }
                    });
                    loadFragment(cuadrosFragment);
                    return true;
                }else if(menuItem.getItemId()==R.id.menu_mapa){
                    mapaFragment=MapaFragment.newInstance("","");
                    loadFragment(mapaFragment);
                    return true;
                }else {
                    return false;
                }
            }
        });


    }
    public void loadFragment(Fragment fragment){
        if(fragmentManager!=null){
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView,fragment);
            fragmentTransaction.commit();
        }
    }
}