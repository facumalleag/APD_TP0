package com.example.myapplication.controller;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.myapplication.MainActivity;
import com.example.myapplication.model.Receta;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RecipesController {

    private ArrayList<Receta> coleccion_recetas= new ArrayList<Receta>();
    private static final String FILE_NAME = "Receta a Subir "+ System.currentTimeMillis()+" .txt";
    private static RecipesController instancia;


    public static RecipesController getInstancia() {
        if (instancia==null) {
            instancia=new RecipesController();
        }
        return instancia;
    }

    public void guardarRecetaEnAlmInterno(Context context) {
        Receta receta=coleccion_recetas.get(coleccion_recetas.size()-1);
        String textoASalvar = new Gson().toJson(receta);//etFile.getText().toString();
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

    public void agregarreceta(Receta receta){
        coleccion_recetas.add(receta);
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
            String aux=stringBuilder.toString();
            JsonObject obj = new JsonParser().parse(aux).getAsJsonObject();//new JsonObject(lineaTexto);
            Log.d("TAG2", "El texto leido es "+ obj);
            Log.d("TAG2", obj.get("name").getAsString());

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
