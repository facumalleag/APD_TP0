package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Called when the user taps the Send button
     */
    public void LoginUsuario() {
       /* Intent intent = new Intent(this, DisplayMessageActivity.class);
        TextView editText = findViewById(R.id.txtvWelcome);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = "Por favor ingrese sus datos de Login.";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void RegistrarUsuario(){

    }
}