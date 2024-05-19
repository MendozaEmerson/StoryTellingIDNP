package com.example.loginsample2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginsample2.databinding.ActivityHomeBinding;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
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
*/
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewEmail = findViewById(R.id.textViewEmail);

        String accountEntityJson= getIntent().getStringExtra("ACCOUNT");
        //el json que recibo lo convierto en un objeto otra vez
        Gson gson= new Gson();
        AccountEntity accountEntity=gson.fromJson(accountEntityJson,AccountEntity.class);

        textViewName.setText(accountEntity.getFirstName());
        textViewEmail.setText(accountEntity.getEmail());

       Log.d("Home Activity", accountEntityJson);

    }
}