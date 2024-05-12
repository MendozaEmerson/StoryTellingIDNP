package com.example.loginsample2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginsample2.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding= ActivityRegisterBinding.inflate(getLayoutInflater());

       // setContentView(binding.getRoot());

        binding.buttonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                registerUser();
            }
        });

        binding.textViewLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(RegisterActivity.this, "Abrir actividad de inicio de sesión", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    private void registerUser(){
        String name= binding.editName.getText().toString().trim();
        String lastName= binding.editLastname.getText().toString().trim();
        String email= binding.editEmail.getText().toString().trim();
        String password=binding.editPasswordRegister.getText().toString().trim();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(lastName)||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_SHORT).show();

        }
    }
}
