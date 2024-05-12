package com.example.loginsample2;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginsample2.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);

        EditText editUsername = binding.editUsername;
        EditText editPassword = binding.editPassword;
        Button btnLogin = binding.btnLogin;
        TextView textViewSignIn = binding.textViewSignIn;
        AccountEntity accountEntity;

        Intent intent= getIntent();
        if (intent != null && intent.hasExtra("account")) {
            Log.d("TAG","recibió intent");
            accountEntity = (AccountEntity) intent.getSerializableExtra("account");
            String user = accountEntity.getUser();
            Log.d("TAG",user);
            String password = accountEntity.getPassword();
            editUsername.setText(user);
            editPassword.setText(password);
        }


        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();

// Iterar sobre la lista de cuentas
                /*for (AccountEntity account : accountEntity.getAccountList()) {
                    // Comparar el nombre de usuario y la contraseña
                    if (username.equals(account.getUser()) && password.equals(account.getPassword())) {
                        // Las credenciales son válidas
                        // Aquí puedes realizar las acciones que deseas después de la autenticación exitosa
                        Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Inicio de sesión exitoso");
                        // Por ejemplo, abrir una nueva actividad
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        return; // Salir del bucle si se encuentra una coincidencia
                    }
                }
                Toast.makeText(getApplicationContext(), "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Credenciales inválidas");*/

               if ((editUsername.getText().toString().equals("admin")||(editUsername.getText().toString().equals("jeff"))&& editPassword.getText().toString().equals("admin")){
                    Toast.makeText(getApplicationContext(), "Bienvenido a mi App", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Bienvenido a mi App");
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("username", editUsername.getText().toString());
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), "Error en la autenticación", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Error en la autenticación");
                }

            }
        });

        textViewSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }
}