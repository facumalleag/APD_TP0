package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistroExitosoLayout extends AppCompatActivity {
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_exitoso_layout);
        layout=findViewById(R.id.pantalla_exito);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarPantallaInicio(v);
            }
        });
    }

    public void mostrarPantallaInicio(View v){
        Intent intent = new Intent(this, HomeApplicationActivity.class);
        startActivity(intent);
    }
}