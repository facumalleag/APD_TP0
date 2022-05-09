package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";

    //Button register=findViewById(R.id.btnRegistro);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarUsuario(v);
            }
        });*/
    }

    /**
     * Called when the user taps the Send button
     */
    public void LoginUsuario(View view) {
       /* Intent intent = new Intent(this, DisplayMessageActivity.class);
        TextView editText = findViewById(R.id.txtvWelcome);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/

        Intent intent = new Intent(this, LoginLayout.class);
        String message = "Por favor ingrese sus datos de Login.";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void RegistrarUsuario(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        String message = "Por favor ingrese sus datos para registrarse";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}