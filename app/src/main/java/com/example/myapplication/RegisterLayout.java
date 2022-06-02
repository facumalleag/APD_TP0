package com.example.myapplication;

import static com.example.myapplication.R.id.editTextTextAlias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.controller.UserController;

import java.util.ArrayList;

public class RegisterLayout extends AppCompatActivity {
    private UserController coleccionUsuarios = UserController.getInstancia();

    Button comenzar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout_display);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.txtViewSubtituloRecupero);
        textView.setText(message);

        comenzar = findViewById(R.id.btnRestablecerPWD);
        comenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContinuarIngresoDatos(v);
            }
        });

        EditText alias = findViewById(R.id.editTextTextAlias);
        alias.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String alias_ingresado=editable.toString();
                if (existeAlias(alias_ingresado)){
                    alias.setError("El alias ingresado ya existe");

                }


            }

            private boolean existeAlias(String alias) {
                //Consultar a la base de datos el alias y devolver resultado de la busqueda.
                return true;
            }
        });

        TextView recupero = findViewById(R.id.txtVRecuperoPWD);
        recupero.setVisibility(View.INVISIBLE);
        EditText email = findViewById(R.id.editTextEmailRecupero);
        email.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Si email es incorrecto da la opcion de recuperar.
                if (editable.toString().length()<3){
                    //email.setError("El mail ingresado ya se encuentra registrado. Si no recuerda su contraseña puede recuperar la misma: "+ recupero);
                    recupero.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    public void ContinuarIngresoDatos(View view) {
        EditText email = findViewById(R.id.editTextEmailRecupero);
        EditText alias = findViewById(editTextTextAlias);
        String dato_email = email.getText().toString();
        String dato_alias = alias.getText().toString();

        if (dato_email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(dato_email).matches()){
            email.setError("Correo invalido");
            return;
        }
        if (dato_alias.isEmpty()){
            email.setError("Alias invalido");
            return;
        }

        if (coleccionUsuarios.validarDaatosRegistro(dato_alias, dato_email)) {
            //enviarmail al usuario acerca de su registracion exitosa
            coleccionUsuarios.enviarMailConfirmacionRegistro(dato_alias, dato_email);
            Intent intent = new Intent(this, AltaUsuarioActivity.class);
            startActivity(intent);
        } else {
            //el alias existe por lo que se le debe sugerir otro similar y pedir q lo vuelva a intertar
            ArrayList<String> sug_alias = coleccionUsuarios.sugerirAlias(dato_alias, dato_email);

        }


    }

    public void RecuperarPassword(View view){
        Intent intent = new Intent(this, RecuperoPWDActivity.class);
        startActivity(intent);

    }

}