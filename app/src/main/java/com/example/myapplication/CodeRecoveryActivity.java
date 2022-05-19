package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.controller.UserController;

public class CodeRecoveryActivity extends AppCompatActivity {
    private UserController coleccionUsuarios=UserController.getInstancia();
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_recovery);
        Bundle extra = getIntent().getExtras();
        String email=extra.toString();


        TextView reenvio=findViewById(R.id.txtVReenvioCod);
        reenvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reenviarCodigo(email);
            }
        });


        /*
        //String email=intent.getExtra();

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STRING_I_NEED");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED"); }*/


        boton=findViewById(R.id.coderecovery);
        boton.setEnabled(false);

        EditText num1 = findViewById(R.id.editTextNumber);
        EditText num2 = findViewById(R.id.editTextNumber2);
        EditText num3 = findViewById(R.id.editTextNumber3);
        EditText num4 = findViewById(R.id.editTextNumber4);
        num1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                num2.requestFocus();
            }
        });

        num2.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                num3.requestFocus();
            }
        });

        num3.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                num4.requestFocus();
            }
        });

        num4.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boton.setEnabled(true);
            }
        });


    }


    public void reenviarCodigo(String email){
        coleccionUsuarios.enviarMailCodeRecovery(email);
    }

}