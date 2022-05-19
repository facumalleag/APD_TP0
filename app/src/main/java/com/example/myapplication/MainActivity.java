package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button start=findViewById(R.id.btnComenzar);
       start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
            }
        });
    }

    /**
     * Called when the user taps the Send button
     */
    public void LoginUsuario(View view) {

        Intent intent = new Intent(this, LoginLayout.class);
        //String message = "Por favor ingrese sus datos de Login.";
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void RegistrarUsuario(View view){
        Intent intent = new Intent(this, RegisterLayout.class);
        String message = "Crear Cuenta";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}