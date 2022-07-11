package com.example.myapplication;

import static com.example.myapplication.Constants.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.controller.UserController;
import com.example.myapplication.model.User;
import com.example.myapplication.services.UserService;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AltaUsuarioActivity extends AppCompatActivity {

    private CheckBox seleccionTermYCond;
    Button btnFinalizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_user_activity);
        seleccionTermYCond =  findViewById(R.id.checkBoxTYC);
        btnFinalizar=findViewById(R.id.btnFinalizarReg);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText password=findViewById(R.id.editTextTextPersonName5);
                EditText nombre,apellido,telefono,pwd,pwd2;
                nombre=findViewById(R.id.editTextTextPersonName);
                apellido=findViewById(R.id.editTextTextPersonName2);
                telefono=findViewById(R.id.editTextTextPersonName4);
                pwd2=findViewById(R.id.textViewPassword);

                String stringname=nombre.getText().toString().trim();
                String stringapellido=apellido.getText().toString().trim();
                String stringtelefono=telefono.getText().toString().trim();
                String stringpassword=password.getText().toString().trim();
                String stringpassword2=pwd2.getText().toString().trim();




                if (stringname.isEmpty()){
                    nombre.setError("Completar campo faltante");
                    return;
                }
                if (stringapellido.isEmpty()){
                    apellido.setError("Completar campo faltante");
                    return;
                }
                if (stringtelefono.isEmpty() ){
                    telefono.setError("Completar campo faltante");
                    return;
                }
                if (stringpassword.isEmpty()){
                    password.setError("Completar campo faltante");
                    return;
                }
                if (stringpassword2.isEmpty()){
                    password.setError("Completar campo faltante");
                    return;
                }

                boolean isOk = compararPasswords(password,pwd2);
                if (!isOk){
                    return;
                }

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                String mail = UserController.getInstancia().getEmail();
                String alias = UserController.getInstancia().getAlias();
                UserService us = retrofit.create(UserService.class);
                User newUser = new User(mail,alias,"",stringname+" "+stringapellido,stringpassword);
                Call<JsonElement> call = us.fullRegister(newUser);

                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        if (response.isSuccessful()) {
                            continuarVentanaDeFinRegistro(v);

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
        });
    }

    public void continuarVentanaDeFinRegistro(View view){
        Intent intent2 = new Intent(this,RegistroExitosoLayout.class);
        startActivity(intent2);
    }

    public void habilitarBtnFinalizar(View v){
        if (seleccionTermYCond.isChecked()){
            btnFinalizar.setEnabled(true);
            btnFinalizar.setBackgroundColor(Color.parseColor("#5985EB"));
        }else {
            btnFinalizar.setEnabled(false);
            btnFinalizar.setBackgroundColor(Color.parseColor("#AEACAD"));
        }
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