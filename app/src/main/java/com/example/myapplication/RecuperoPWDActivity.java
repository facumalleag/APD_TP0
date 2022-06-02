package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.myapplication.controller.UserController;
import com.google.android.material.textfield.TextInputEditText;

public class RecuperoPWDActivity extends AppCompatActivity {
    private static final String EXTRA_MESSAGE = "";
    private UserController coleccionUsuarios=UserController.getInstancia();

    Button btn_restablecer;
    TextInputEditText editTextEmailRecupero;
    ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupero_pwd);

        loadingProgressBar=findViewById(R.id.IDprogressBar);
        loadingProgressBar.setVisibility(View.GONE);

        btn_restablecer=findViewById(R.id.btnRestablecerPWD);
        editTextEmailRecupero=findViewById((R.id.editTextEmailRecupero));

        btn_restablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=editTextEmailRecupero.getText().toString().trim();

                if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmailRecupero.setError("Correo invalido");
                    return;
                }
                loadingProgressBar.setVisibility(View.VISIBLE);
                coleccionUsuarios.enviarMailCodeRecovery(email);
                continuarIngresoCodigo(email);
            }
        });

    }

    private void continuarIngresoCodigo(String email) {
        Intent intent = new Intent(this,CodeRecoveryActivity.class);
        intent.putExtra(EXTRA_MESSAGE, email);
        startActivity(intent);
    }
/*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }*/
}

