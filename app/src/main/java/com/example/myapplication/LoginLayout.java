package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginLayout extends AppCompatActivity {
    private EditText emailEditText,passwordEditText;
    private Button loginButton;
    TextView olvidasteContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout_display);

        EditText emailEditText = findViewById(R.id.editTextEmailRecupero);
        EditText passwordEditText = findViewById(R.id.editTextTextPassword);
        Button loginButton = findViewById(R.id.Login);
        olvidasteContrasena=findViewById(R.id.olvidasteContra);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = "Por favor ingrese sus datos de Login.";

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.txtViewSubtituloRecupero);
        textView.setText(message);

        olvidasteContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarRecoverPassword(v);
            }
        });

    }

    public void iniciarRecoverPassword(View v) {
        Intent intent = new Intent(this, RecuperoPWDActivity.class);
        //intent.putExtra(Intent.EXTRA_EMAIL,emailEditText.getText().toString());
        String message = "Ingrese su email y presione 'Next'. Se le enviará un codigo para completar en la siguiente pagina";
        intent.putExtra(Intent.EXTRA_TEXT, message);
        //intent.putExtra(Intent.EXTRA_EMAIL, emailEditText.getText().toString());
        startActivity(intent);
    }

    public void ActionLogin(View view){

        String mail= emailEditText.getText().toString();
        String password=passwordEditText.getText().toString();

        if (ComprobarLogin(mail,password)==true){
            Intent intent = new Intent(this, HomeApplicationActivity.class);
            //intent.putExtra(Intent.EXTRA_EMAIL,emailEditText.getText().toString());
            startActivity(intent);
        }

    }

    public boolean ComprobarLogin (String mail, String pwd){
        boolean resultado=true;

        return resultado;

    }

}