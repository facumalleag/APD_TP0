package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";
//    TextView olvidasteContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button start=findViewById(R.id.btnComenzar);
        start.setOnClickListener(v -> setContentView(R.layout.activity_main));
/*
        Button start=findViewById(R.id.btnComenzar);
        start.setOnClickListener(v -> setContentView(R.layout.activity_main));
*/
    }

    /*
         *
         *
         *         <activity
                android:name=".WelcomeActivity"
                android:exported="false" />

        public void LoginUsuario(View view) {

            Intent intent = new Intent(this, LoginLayout.class);
            //String message = "Por favor ingrese sus datos de Login.";
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);

        }

        /**
         * Called when the user taps Register
         */
    public void RegistrarUsuario(View view){
        Intent intent = new Intent(this, RegisterLayout.class);
        String message = "Crear Cuenta";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /**
     * Called when the user taps Recupero Password
     */

    public void iniciarRecoverPassword(View v) {
        Intent intent = new Intent(this, RecuperoPWDActivity.class);
        //intent.putExtra(Intent.EXTRA_EMAIL,emailEditText.getText().toString());
        String message = "Ingrese su email y presione 'Next'. Se le enviará un codigo para completar en la siguiente pagina";
        intent.putExtra(Intent.EXTRA_TEXT, message);
        //intent.putExtra(Intent.EXTRA_EMAIL, emailEditText.getText().toString());
        startActivity(intent);
    }
    /**
     * Called when the user taps Iniciar sesion
     */

    public void ActionLogin(View view){
        EditText emailEditText = findViewById(R.id.editTextEmailRecupero);
        EditText passwordEditText = findViewById(R.id.editTextTextPassword);

        String mail= emailEditText.getText().toString();
        String password=passwordEditText.getText().toString();

        if (ComprobarLogin(mail,password)==true){
            Intent intent = new Intent(this, HomeApplicationActivity.class);
            //intent.putExtra(Intent.EXTRA_EMAIL,emailEditText.getText().toString());
            startActivity(intent);
        }

    }

    /**
     * Called to review the user/password entered
     */

    public boolean ComprobarLogin (String mail, String pwd){
        boolean resultado=true;

        return resultado;

    }


}