package com.example.myapplication;

import static com.example.myapplication.Constants.BASE_URL;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.controller.NetworkController;
import com.example.myapplication.controller.RecipesController;
import com.example.myapplication.controller.UserController;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonElement;

import com.example.myapplication.services.UserService;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements NetworkController.ReceiverListener {
    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";
    private NetworkController controlador_red=NetworkController.getInstancia();
    private DialogoRedDisponible dialogo=new DialogoRedDisponible();
    private UserController coleccionUsuarios=UserController.getInstancia();
    private RecipesController recetas=RecipesController.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button start=findViewById(R.id.btnComenzar);
        start.setOnClickListener(v -> setContentView(R.layout.activity_main));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Verifica permisos para Android 6.0+
            checkExternalStoragePermission();
        }
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
        String message = "Ingrese su email y presione 'Siguiente'. Se le enviará un codigo para completar en la siguiente pagina";
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(intent);
    }
    /**
     * Called when the user taps Iniciar sesion
     */

    public void ActionLogin(View view){
        EditText emailEditText=findViewById(R.id.editTextEmailRecupero);
        EditText passwordEditText=findViewById(R.id.editTextTextPassword);
        String email1=emailEditText.getText().toString().trim();
        if (email1.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            emailEditText.setError("Correo invalido");
            return;
        }
        String tipoconexion=controlador_red.verificarTipoRed(this);
        mostrarAlerta(tipoconexion);


        String mail= emailEditText.getText().toString();
        String password=passwordEditText.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService us = retrofit.create(UserService.class);
        Call<JsonElement> call = us.login(mail,password);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                //lblEstado.setText(response.body() );
                System.out.println(response.body());
                if(response.isSuccessful()){
                    String name = response.body().getAsJsonObject().get("data").getAsJsonObject().get("user").getAsJsonObject().get("name").getAsString();
                    String email = response.body().getAsJsonObject().get("data").getAsJsonObject().get("user").getAsJsonObject().get("email").getAsString();
                    String alias = response.body().getAsJsonObject().get("data").getAsJsonObject().get("user").getAsJsonObject().get("alias").getAsString();
                    Integer id = response.body().getAsJsonObject().get("data").getAsJsonObject().get("user").getAsJsonObject().get("id").getAsInt();
                    coleccionUsuarios.setDatosUsuarios(email,alias,id,name);
                    doLogin();
                }else{
                    if (response.code() ==400){
                        Toast toast = Toast.makeText(getApplication().getApplicationContext(), "Correo o contraseña invalida", Toast.LENGTH_SHORT);
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


    private void checkExternalStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Se tiene permiso para leer!");
        }
    }

    /**
     * metodo para mostrar mensaje de alerta cuando el usuario este usando datos del celular
     *
     */

    public void doLogin (){
        String tipoconexion=controlador_red.verificarTipoRed(this);
        if (tipoconexion.equals("MOBILE")) {
            //alertaRed.show(getSupportFragmentManager(), "Atención");
            Intent alertared = new Intent(this, AlertaRedWifiActivity.class);
            //intent.putExtra(Intent.EXTRA_EMAIL,emailEditText.getText().toString());
            startActivity(alertared);

            if (controlador_red.getOpcionNavegarSinWifi() == true) {
                Intent intent = new Intent(this, HomeApplicationActivity.class);
                startActivity(intent);
            }
        }else {
            if(tipoconexion.equals("WIFI")) {
                Intent intent = new Intent(this, HomeApplicationActivity.class);
                startActivity(intent);
            }else
                showSnackBar(tipoconexion);
        }

    }


    public void mostrarAlerta(String tipoconexion) {
        if (tipoconexion.equals("MOBILE")) {
            DialogFragment newFragment = new DialogoRedDisponible();
            newFragment.show(getSupportFragmentManager(), "Atención");
        }else{
            if(!tipoconexion.equals("WIFI")){
                showSnackBar(tipoconexion);
            }else{
                //onDialogPositiveClick(dialogo);
            }
        }
    }



    private void showSnackBar(String isConnected) {

        // initialize color and message
        String message;
        int color=Color.RED;;
        message = "Not Connected to Internet";

        // initialize snack bar
        Snackbar snackbar = Snackbar.make(findViewById(R.id.btnLogin), message, Snackbar.LENGTH_LONG);

        // initialize view
        View view = snackbar.getView();

        // Assign variable
        TextView textView = view.findViewById(R.id.snackbar_text);

        // set text color
        textView.setTextColor(color);

        // show snack bar
        snackbar.show();
    }



    @Override
    public void onNetworkChange(boolean isConnected) {
        //showSnackBar(isConnected);
        //mostrarAlerta("WIFI");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }



}