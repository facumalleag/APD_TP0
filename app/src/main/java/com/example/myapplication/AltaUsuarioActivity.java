package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

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