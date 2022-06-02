package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class AltaUsuarioActivity extends AppCompatActivity {

    private CheckBox seleccionTermYCond;
    Button btnFinalizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_user_activity);
        seleccionTermYCond =  findViewById(R.id.checkBoxTYC);
        btnFinalizar=findViewById(R.id.btnFinalizarReg);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mail=findViewById(R.id.editTextTextPersonName5);
                EditText nombre,apellido,telefono,pwd;
                nombre=findViewById(R.id.editTextTextPersonName);
                apellido=findViewById(R.id.editTextTextPersonName2);
                telefono=findViewById(R.id.editTextTextPersonName4);
                pwd=findViewById(R.id.editTextTextPersonName6);

                String stringname=nombre.getText().toString().trim();
                String stringapellido=apellido.getText().toString().trim();
                String stringtelefono=telefono.getText().toString().trim();
                String stringpassword=pwd.getText().toString().trim();

                String email=mail.getText().toString().trim();

                if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mail.setError("Correo invalido");
                    return;
                }

                if (stringname.isEmpty()){
                    nombre.setError("Completar campo faltante");
                    return;
                }
                if (stringapellido.isEmpty()){
                    apellido.setError("Completar campo faltante");
                    return;
                }
                if (stringtelefono.isEmpty() ){
                    telefono.setError("Completar campo faltante");
                    return;
                }
                if (stringpassword.isEmpty()){
                    pwd.setError("Completar campo faltante");
                    return;
                }



                continuarVentanaDeFinRegistro(v);
            }
        });
    }

    public void continuarVentanaDeFinRegistro(View view){
        Intent intent2 = new Intent(this,RegistroExitosoLayout.class);
        startActivity(intent2);
    }

    public void habilitarBtnFinalizar(View v){
        if (seleccionTermYCond.isChecked()){
            btnFinalizar.setEnabled(true);
            btnFinalizar.setBackgroundColor(Color.parseColor("#5985EB"));
        }else {
            btnFinalizar.setEnabled(false);
            btnFinalizar.setBackgroundColor(Color.parseColor("#AEACAD"));
        }
    }
}