package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class RecetaGuardadaLayout extends AppCompatActivity {
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardado_exitoso_layout);
        layout=findViewById(R.id.pantalla_guardado_exitoso);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarPantallaInicio();
            }
        }, 5000);
    }

    public void mostrarPantallaInicio(){
        Intent intent = new Intent(this, HomeApplicationActivity.class);
        startActivity(intent);
    }
}