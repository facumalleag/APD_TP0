package com.example.myapplication;

import static com.example.myapplication.R.id.editTextTextAlias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.controller.UserController;
import com.example.myapplication.model.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.example.myapplication.services.UserService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        TextView registro_incompleto = findViewById(R.id.txtRegistroIncompleto);
        registro_incompleto.setVisibility(View.INVISIBLE);

        EditText alias = findViewById(R.id.editTextTextAlias);
        /*
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
*/
        TextView recupero = findViewById(R.id.txtVRecuperoPWD);
        recupero.setVisibility(View.INVISIBLE);
        EditText email = findViewById(R.id.editTextEmailRecupero);
  /*
        email.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
*/

    }

    public void ContinuarIngresoDatos(View view) {
        TextView recupero = findViewById(R.id.txtVRecuperoPWD);
        recupero.setVisibility(View.INVISIBLE);

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService us = retrofit.create(UserService.class);
        Call<JsonElement> call = us.verifyEmailAndAlias(dato_email,dato_alias);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    halfRegister(dato_email,dato_alias);

                }else{
                    try {
                        JsonObject JsonResponse = new JsonParser().parse(response.errorBody().string() ).getAsJsonObject();
                        if (response.code() ==430){//Alias e email en uso
                            //Toast.makeText(JsonResponse.get("message").toString()).show();
                            ArrayList<String> sug_alias = coleccionUsuarios.sugerirAlias(dato_alias, dato_email);
                            TextView recupero = findViewById(R.id.txtVRecuperoPWD);
                            recupero.setVisibility(View.VISIBLE);
                            EditText alias2 = findViewById(R.id.editTextTextAlias);
                            alias2.setError("El alias ingresado ya existe");
                        }
                        if (response.code() ==433){//alias en uso
                            EditText alias2 = findViewById(R.id.editTextTextAlias);
                            ArrayList<String> sug_alias = sugerirAlias(dato_alias, dato_email);

                            alias2.setError("El alias ingresado ya existe. Le sugerimos estos alias\n"+"- "+sug_alias.get(0)+"\n- "+sug_alias.get(1)+"\n- "+sug_alias.get(2));

                        }
                        if (response.code() ==432){//email en uso
                            TextView registro_incompleto = findViewById(R.id.txtRegistroIncompleto);
                            registro_incompleto.setVisibility(View.VISIBLE);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }
    public void sendEmail(String email, String alias){
        coleccionUsuarios.enviarMailConfirmacionRegistro(alias, email);
        Intent intent = new Intent(this, CodeForRegisterActivity.class);
        startActivity(intent);
    }

    public void halfRegister(String email, String alias){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService us = retrofit.create(UserService.class);
        User newUser = new User(email,alias,"","","");
        Call<JsonElement> call = us.halfRegister(newUser);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    sendEmail(email, alias);

                } else {
                    if (response.code() == 400) {
                        Toast toast = Toast.makeText(getApplication().getApplicationContext(), "Ocurrio un error intente mas tarde", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void RecuperarPassword(View view){
        Intent intent = new Intent(this, RecuperoPWDActivity.class);
        startActivity(intent);

    }

    public void derivarSoporteDeSitio(View view){
        Intent intent = new Intent(this, SoporteUsuarioActivity.class);
        startActivity(intent);

    }

    public ArrayList<String> sugerirAlias(String dato_alias, String dato_mail) {
        ArrayList<String> sugerencias= new ArrayList<String>();

        // falta comparar que la sugerencia no exista en la base
        String aux=dato_mail.substring(0,dato_mail.indexOf("@")); //obtengo solo usermail
        String sugerencia1=dato_alias + "_" + aux;
        String sugerencia2=aux+ "_"+ aux.substring(0,3);
        String sugerencia3=dato_alias + dato_alias.substring(0,3);
        sugerencias.add(sugerencia1);
        sugerencias.add(sugerencia2);
        sugerencias.add(sugerencia3);

        return sugerencias;
    }

}