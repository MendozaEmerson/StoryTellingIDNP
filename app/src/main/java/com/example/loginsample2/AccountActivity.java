package com.example.loginsample2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginsample2.databinding.ActivityAccountBinding;
import com.google.gson.Gson;


public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    AccountEntity myaccountEntity= new AccountEntity();

    public final static String ACCOUNT_RECORD= "ACCOUNT_RECORD";
    public final static Integer ACCOUNT_ACEPTAR= 100;
    public final static Integer ACCOUNT_CANCELAR= 200;
    public final static String PREFS_NAME= "UserPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnAceptar = findViewById(R.id.btnOK);
        Button btnCancelar=findViewById(R.id.btnCancel);
        EditText editFirstName = findViewById(R.id.editFirstName);
        EditText editLastName = findViewById(R.id.editLastName);
        EditText editEmail = findViewById(R.id.edtEmail);
        EditText editPhone = findViewById(R.id.editTextPhone);
        EditText editUsername1 = findViewById(R.id.editUsername1);
        EditText editPassword1 = findViewById(R.id.editPassword1);


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountEntity accountEntity= new AccountEntity();

                accountEntity.setFirstName(editFirstName.getText().toString());
                accountEntity.setLastName(editLastName.getText().toString());
                accountEntity.setEmail(editEmail.getText().toString());
                accountEntity.setPhone(editPhone.getText().toString());
                accountEntity.setUser(editUsername1.getText().toString());
                accountEntity.setPassword(editPassword1.getText().toString());

                Gson gson= new Gson();
                String accountJson=gson.toJson(accountEntity);

                Intent data= new Intent(AccountActivity.this, LoginActivity.class);
                data.putExtra(ACCOUNT_RECORD, accountJson);

                SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USERNAME", editUsername1.getText().toString());
                editor.putString("PASSWORD", editPassword1.getText().toString());
                editor.apply();

                setResult(ACCOUNT_ACEPTAR, data);
                finish();

                /*
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                intent.putExtra("account", myaccountEntity);
                Log.d("TAG", editUsername.getText().toString());
                Log.d("TAG", myaccountEntity.getUser());
                startActivity(intent);
                */

            }
        });

        btnCancelar.setOnClickListener(view -> {
            setResult(ACCOUNT_CANCELAR);
            finish();
        }

        );
    }
}