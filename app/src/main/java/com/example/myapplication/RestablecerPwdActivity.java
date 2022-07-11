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

import com.example.myapplication.controller.UserController;
import com.example.myapplication.services.UserService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                boolean isOk = compararPasswords(nueva_pwd,confirma_pwd);
                if (!isOk){
                    return;
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserService us = retrofit.create(UserService.class);
                String mail = UserController.getInstancia().getEmail();
                String newpwd=nueva_pwd.getText().toString().trim();
                Call<JsonElement> call = us.updatePassword(mail,newpwd);

                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        //lblEstado.setText(response.body() );
                        System.out.println(response.body());
                        if(response.isSuccessful()){
                            String message = "Contraseña modificada correctamente";
                            Toast toast = Toast.makeText(getApplication().getApplicationContext(), message, Toast.LENGTH_SHORT);
                            toast.show();
                            volverPantallaLogin();
                        }else{
                            if (response.code() ==400){
                                Toast toast = Toast.makeText(getApplication().getApplicationContext(), "Contraseña invalida", Toast.LENGTH_SHORT);
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

    public  boolean compararPasswords(TextView nueva_pwd, TextView confirma_pwd) {
        String newpwd=nueva_pwd.getText().toString().trim();
        String confirmpwd=confirma_pwd.getText().toString().trim();
        if (newpwd.isEmpty()&& confirmpwd.isEmpty()){
            nueva_pwd.setError("Por favor complete los datos requeridos");
            confirma_pwd.setError("Por favor complete los datos requeridos");
            //restablecer.setEnabled(false);
            //restablecer.setBackgroundColor(Color.parseColor("#AEACAD"));
            return false;
        }else{
            if (confirmpwd.isEmpty()){
                confirma_pwd.setError("Por favor complete los datos requeridos");
                return false;
            }
            else{
                if (newpwd.isEmpty()){
                    nueva_pwd.setError("Por favor complete los datos requeridos");
                    return false;
                }

            }

        }
        if(!newpwd.equals(confirmpwd)){
            confirma_pwd.setError("Las contraseñas no coinciden. Por favor revisa los datos");
            nueva_pwd.setError("Las contraseñas no coinciden. Por favor revisa los datos");
            //restablecer.setEnabled(false);
            //restablecer.setBackgroundColor(Color.parseColor("#AEACAD"));
            return false;
        }
        if(newpwd.equals(confirmpwd)) {
            return true;
        }
        return true;
    }

}