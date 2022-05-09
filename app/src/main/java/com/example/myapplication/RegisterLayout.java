package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.controller.UserController;

import java.util.ArrayList;

public class RegisterLayout extends AppCompatActivity {
    private UserController coleccionUsuarios=UserController.getInstancia();

    Button siguiente=findViewById(R.id.btnSiguiente);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout_display);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContinuarIngresoDatos(v);
            }
        });

    }

    public void ContinuarIngresoDatos(View view){
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText alias = findViewById(R.id.editTextTextAlias);
        String dato_email = email.getText().toString();
        String dato_alias = alias.getText().toString();


        if (coleccionUsuarios.validarDaatosRegistro(dato_alias,dato_email)==true){
            //enviarmail al usuario acerca de su registracion exitosa
            coleccionUsuarios.enviarMailConfirmacionRegistro(dato_alias,dato_email);
            Intent intent = new Intent(this, AltaUsuarioActivity.class);
            startActivity(intent);
        }
        else{
            //el alias existe por lo que se le debe sugerir otro similar y pedir q lo vuelva a intertar
            ArrayList<String> sug_alias= coleccionUsuarios.sugerirAlias(dato_alias,dato_email);

        }


    }

}