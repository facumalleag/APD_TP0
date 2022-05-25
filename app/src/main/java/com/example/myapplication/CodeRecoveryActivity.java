package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
    TextView reenvio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_recovery);
        Intent intent = getIntent();
        String email = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        reenvio=findViewById(R.id.txtVReenvioCod);
        reenvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reenviarCodigo(email);
                reenvio.setEnabled(false);
                reenvio.setBackgroundColor(4124);
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


        boton=findViewById(R.id.btnCodeRecovery);

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
                if (num1.length()<1){
                    num1.requestFocus();
                }else{
                    num2.requestFocus();
                }
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

                if (num2.length()<1){
                    num1.requestFocus();
                }else{
                    num3.requestFocus();
                }
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
                if (num3.length()<1){
                    num2.requestFocus();
                }else{
                    num4.requestFocus();
                }

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
                if (num4.length()<1){
                    num3.requestFocus();
                    boton.setEnabled(false);
                    boton.setBackgroundColor(Color.parseColor("#AEACAD"));
                }else{
                    boton.setEnabled(true);
                    boton.setBackgroundColor(Color.parseColor("#5985EB"));
                }

            }
        });


    }


    public void reenviarCodigo(String email){
        coleccionUsuarios.enviarMailCodeRecovery(email);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,RegisterLayout.class);
        startActivity(intent);
        finish();
    }

}