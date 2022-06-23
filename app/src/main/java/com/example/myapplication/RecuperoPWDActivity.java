package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.controller.UserController;
import com.example.myapplication.services.UserService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                editTextEmailRecupero.setError(null);
                if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmailRecupero.setError("Correo invalido");
                    return;
                }
                loadingProgressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserService us = retrofit.create(UserService.class);
                Call<JsonElement> call = us.verifyEmail(email);

                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        //lblEstado.setText(response.body() )
                        if(response.isSuccessful()){
                            continuarIngresoCodigo(email);
                            Toast toast = Toast.makeText(getApplication().getApplicationContext(), "pp", Toast.LENGTH_LONG);
                        }else{
                            if (response.code() ==400){
                                Toast toast = Toast.makeText(getApplication().getApplicationContext(), "No se encuentra ningun usuario asociado a este email", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            if (response.code() ==433){
                                continuarSoporteaUsuario();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });

                loadingProgressBar.setVisibility(View.INVISIBLE);



            }
        });

    }

    private void continuarIngresoCodigo(String email) {
        coleccionUsuarios.enviarMailCodeRecovery(email);
        Intent intent = new Intent(this,CodeRecoveryActivity.class);
        intent.putExtra(EXTRA_MESSAGE, email);
        startActivity(intent);
    }
    private void continuarSoporteaUsuario() {
        Intent intent = new Intent(this,SoporteUsuarioActivity.class);
        startActivity(intent);
    }

    public void onBackPressed(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

