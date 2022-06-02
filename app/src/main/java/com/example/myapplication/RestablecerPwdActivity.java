package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class RestablecerPwdActivity extends AppCompatActivity {
    Button restablecer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_pwd);

        restablecer=findViewById(R.id.btnRestablecerContra);
        restablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView confirma_pwd=findViewById(R.id.edittxtConfirmContra);
                TextView nueva_pwd=findViewById(R.id.edittxtNuevaContra);

                compararPasswords(nueva_pwd,confirma_pwd);
            }
        });

    }

    public void volverPantallaLogin() {

       /* int color = Color.GREEN;
        int fondo=Color.DKGRAY;
        Snackbar snackbar = Snackbar.make(findViewById(R.id.restablecer_pwd_vista), message, Snackbar.LENGTH_LONG);


        // initialize view
        View view = snackbar.getView();

        // Assign variable
        TextView textView = view.findViewById(R.id.snackbar_text);

        // set text color
        textView.setTextColor(color);
        textView.setBackgroundColor(fondo);

        // show snack bar
        snackbar.show();
*/
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public  void compararPasswords(TextView nueva_pwd, TextView confirma_pwd) {
        String newpwd=nueva_pwd.getText().toString().trim();
        String confirmpwd=confirma_pwd.getText().toString().trim();
        if (newpwd.isEmpty()&& confirmpwd.isEmpty()){
            nueva_pwd.setError("Por favor complete los datos requeridos");
            confirma_pwd.setError("Por favor complete los datos requeridos");
            //restablecer.setEnabled(false);
            //restablecer.setBackgroundColor(Color.parseColor("#AEACAD"));
            return;
        }else{
            if (confirmpwd.isEmpty()){
                confirma_pwd.setError("Por favor complete los datos requeridos");
                return;
            }
            else{
                if (newpwd.isEmpty()){
                    nueva_pwd.setError("Por favor complete los datos requeridos");
                    return;
                }

            }

        }
        if(!newpwd.equals(confirmpwd)){
            confirma_pwd.setError("Las contraseñas no coinciden. Por favor revisa los datos");
            nueva_pwd.setError("Las contraseñas no coinciden. Por favor revisa los datos");
            //restablecer.setEnabled(false);
            //restablecer.setBackgroundColor(Color.parseColor("#AEACAD"));
            return;
        }

        if(newpwd.equals(confirmpwd)){
            String message = "Contraseña modificada correctamente";
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
            toast.show();
            volverPantallaLogin();
        }

    }
}