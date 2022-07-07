package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.controller.NetworkController;

public class AlertaRedWifiActivity extends AppCompatActivity implements DialogoAlertaRed.AvisarAlertaDialogListener{
    private NetworkController controlador_red=NetworkController.getInstancia();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogFragment alertaDialogFragment = new DialogoAlertaRed();
        alertaDialogFragment.show(getSupportFragmentManager(), "Atención");

    }


    @Override
    public void onClickPositivo(DialogFragment dialog) {
        Log.d("Accept Alert", "Continue with next activity");
        controlador_red.setNavegarSinWifi(true);
        Intent intent = new Intent(this, HomeApplicationActivity.class);
        //intent.putExtra(Intent.EXTRA_EMAIL,emailEditText.getText().toString());
        startActivity(intent);
        finish();
    }

    @Override
    public void onClickNegativo(DialogFragment dialog) {
        Log.d("Refuse Alert", "No continue");
        controlador_red.setNavegarSinWifi(false);
        finish();
    }
}