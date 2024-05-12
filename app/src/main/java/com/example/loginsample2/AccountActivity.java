package com.example.loginsample2;

import android.content.Intent;
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


public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    AccountEntity myaccountEntity= new AccountEntity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText editFirstName = binding.editFirstName;
        EditText editLastName = binding.editLastName;
        EditText editEmail = binding.edtEmail;
        EditText editTextPhone = binding.editTextPhone;
        EditText editUsername = binding.editUsername1;
        EditText editPassword1 = binding.editPassword1;

        Button btnOk = binding.btnOK;
        Button btnCancel = binding.btnCancel;

        TextView textViewRedirectLogin = binding.textViewRedirectLogin;

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myaccountEntity.setFirstName(editFirstName.getText().toString());
                myaccountEntity.setLastName(editLastName.getText().toString());
                myaccountEntity.setEmail(editEmail.getText().toString());
                myaccountEntity.setPhone(editTextPhone.getText().toString());
                myaccountEntity.setUser(editUsername.getText().toString());
                myaccountEntity.setPassword(editPassword1.getText().toString());

                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                intent.putExtra("account", myaccountEntity);
                Log.d("TAG", editUsername.getText().toString());
                Log.d("TAG", myaccountEntity.getUser());
                startActivity(intent);
            }
        });
    }
}