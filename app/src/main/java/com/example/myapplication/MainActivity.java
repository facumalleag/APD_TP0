package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.controller.NetworkController;
import com.example.myapplication.controller.UserController;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity implements NetworkController.ReceiverListener,DialogoRedDisponible.NoticeDialogListener {
    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";
    private NetworkController controlador_red=NetworkController.getInstancia();
    private DialogoRedDisponible dialogo=new DialogoRedDisponible();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button start=findViewById(R.id.btnComenzar);
        start.setOnClickListener(v -> setContentView(R.layout.activity_main));

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
        EditText mail=findViewById(R.id.editTextEmailRecupero);
        String email=mail.getText().toString().trim();

        if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mail.setError("Correo invalido");
            return;
        }
        // verifico el esatdo de la red antes de consultar a la base de datos
        String tipoconexion=controlador_red.verificarTipoRed(this);
        mostrarAlerta(tipoconexion);
    }

    /**
     * metodo para mostrar mensaje de alerta cuando el usuario este usando datos del celular
     *
     */

    public void mostrarAlerta(String tipoconexion) {
        if (tipoconexion.equals("MOBILE")) {
            DialogFragment newFragment = new DialogoRedDisponible();
            newFragment.show(getSupportFragmentManager(), "Atención");
        }else{
            if(!tipoconexion.equals("WIFI")){
                showSnackBar(tipoconexion);
            }else{
                onDialogPositiveClick(dialogo);
            }
        }
    }



        /**
         * Called to review the user/password entered
         * @param mail,pwd
         */

    public boolean ComprobarLogin (String mail, String pwd){
        boolean resultado=true;

        return resultado;

    }


    private void showSnackBar(String isConnected) {

        // initialize color and message
        String message;
        int color;

        // check condition
        if (isConnected.equals("WIFI")) {

            // when internet is connected
            // set message
            message = "Connected to WIFI Internet";

            // set text color
            color = Color.WHITE;

        } else {
            if (isConnected.equals("MOBILE")){
                message="Connected to MOBILE Internet";
                color = Color.MAGENTA;
            }else {

                // when internet
                // is disconnected
                // set message
                message = "Not Connected to Internet";
                // set text color
                color = Color.RED;
            }
        }

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
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
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

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}