package com.example.myapplication.controller;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.myapplication.MainActivity;
import com.example.myapplication.model.Receta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RecipesController {

    private ArrayList<Receta> coleccion_recetas= new ArrayList<Receta>();
    private static final String FILE_NAME = "Receta a Subir.txt";
    private static RecipesController instancia;


    public static RecipesController getInstancia() {
        if (instancia==null) {
            instancia=new RecipesController();
        }
        return instancia;
    }

    public void guardarRecetaEnAlmInterno(Context context) {
        String textoASalvar = "Receta de hamburguesas a Guardar en formato JSON";//etFile.getText().toString();
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(textoASalvar.getBytes());
            Log.d("TAG1", "Fichero Salvado en: " + context.getFilesDir() + "/" + FILE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null){
                try{
                    fileOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }


    public void leerArchivoReceta(Context context) {
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = context.openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineaTexto;
            StringBuilder stringBuilder = new StringBuilder();
            while((lineaTexto = bufferedReader.readLine())!=null){
                stringBuilder.append(lineaTexto).append("\n");
            }
            Log.d("TAG2", "El texto leido es "+stringBuilder);
        }catch (Exception e){

        }finally {
            if(fileInputStream !=null){
                try {
                    fileInputStream.close();
                }catch (Exception e){

                }
            }
        }
    }

}
