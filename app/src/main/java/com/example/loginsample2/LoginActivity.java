package com.example.loginsample2;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.example.loginsample2.AccountActivity.PREFS_NAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginsample2.databinding.ActivityMainBinding;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AccountEntity accountEntity;
    private String accountEntityString;
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
        /*
        Intent intent= getIntent();
        if (intent != null && intent.hasExtra("account")) {
            Log.d("TAG","recibió intent");
            accountEntity = (AccountEntity) intent.getSerializableExtra("account");
            String user = accountEntity.getUser();
            Log.d("TAG",user);
            String password = accountEntity.getPassword();
            editUsername.setText(user);
            editPassword.setText(password);
        }*/

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("USERNAME", "");
        String savedPassword = sharedPreferences.getString("PASSWORD", "");

        editUsername.setText(savedUsername);
        editPassword.setText(savedPassword);

        btnLogin.setOnClickListener(v -> {
            String username= editUsername.getText().toString();
            String password= editPassword.getText().toString();

           if (username.equals(accountEntity.getUser()) && password.equals(accountEntity.getPassword())){
                Toast.makeText(getApplicationContext(), "Bienvenido a mi App", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Bienvenido a mi App");

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("ACCOUNT", accountEntityString);
                startActivity(intent);

            } else{
                Toast.makeText(getApplicationContext(), "Error en la autenticación", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Error en la autenticación");
            }

        });

        textViewSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
            activityResultLauncher.launch(intent);
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            activityResult -> {
                Integer resultCode= activityResult.getResultCode();
                if (resultCode==AccountActivity.ACCOUNT_ACEPTAR){
                    Intent data= activityResult.getData();
                    accountEntityString=data.getStringExtra(AccountActivity.ACCOUNT_RECORD);

                    Gson gson= new Gson();
                    accountEntity=gson.fromJson(accountEntityString, AccountEntity.class);

                    String firstName= accountEntity.getFirstName();
                    Log.d("Login Activity", "Nombre: "firstName);
                    Toast.makeText(getApplicationContext(), "Nombre:"+firstName, Toast.LENGTH_SHORT).show();

                }else if(resultCode==AccountActivity.ACCOUNT_CANCELAR){
                    Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                }

            }
    );
}