package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.controller.UserController;
import com.google.android.material.snackbar.Snackbar;

public class CodeForRegisterActivity extends AppCompatActivity {
    private UserController coleccionUsuarios=UserController.getInstancia();
    Button boton;
    TextView reenvio;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_recovery);
        Intent intent = getIntent();
        email = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

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
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText num1 = findViewById(R.id.editTextNumber);
                EditText num2 = findViewById(R.id.editTextNumber2);
                EditText num3 = findViewById(R.id.editTextNumber3);
                EditText num4 = findViewById(R.id.editTextNumber4);
                String codigo=num1.getText().toString()+num2.getText().toString()+num3.getText().toString()+num4.getText().toString();
                boolean resultado=compararCodigIngresado(codigo,email);
                showSnackBar(resultado);
                restablecerPWD();
            }
        });



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

    public void restablecerPWD() {
        Intent intent = new Intent(this,RestablecerPwdActivity.class);
        startActivity(intent);

    }

    private void showSnackBar(boolean codigo_correcto) {
        String message = null;
        int color = 0;

        if (!codigo_correcto) {
            message = "Codigo Incorrecto";
            color = Color.RED;
        }

        // initialize snack bar
        Snackbar snackbar = Snackbar.make(findViewById(R.id.btnCodeRecovery), message, Snackbar.LENGTH_LONG);

        // initialize view
        View view = snackbar.getView();

        // Assign variable
        TextView textView = view.findViewById(R.id.snackbar_text);

        // set text color
        textView.setTextColor(color);

        // show snack bar
        snackbar.show();
    }

    public boolean compararCodigIngresado(String input_cod,String mail){
        int cod= coleccionUsuarios.getCodRecupero(mail);

        return true;

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