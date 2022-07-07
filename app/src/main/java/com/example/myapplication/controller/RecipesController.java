package com.example.myapplication.controller;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.myapplication.MainActivity;
import com.example.myapplication.model.Receta;
import com.google.android.material.chip.Chip;
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
import java.util.List;

public class RecipesController {

    private ArrayList<Receta> coleccion_recetas= new ArrayList<Receta>();
    private static final String FILE_NAME = "Receta a Subir.txt";
    private static RecipesController instancia;
    private List<Integer> category_for_search = new ArrayList<Integer>();
    private List<Integer> ingredients_for_search = new ArrayList<Integer>();
    private List<Integer> lack_of_ingredients_for_search = new ArrayList<Integer>();
    private ArrayList<Chip> Ccategory_for_search = new ArrayList<Chip>();
    private ArrayList<Chip> Cingredients_for_search = new ArrayList<Chip>();
    private ArrayList<Chip> Clack_of_ingredients_for_search = new ArrayList<Chip>();


    private String user_name_for_search = "";
    private String recipe_for_search = "";


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

    public List<Integer> getCategory_for_search() {
        return category_for_search;
    }

    public void setCategory_for_search(ArrayList<Integer> category_for_search) {
        this.category_for_search = category_for_search;
    }

    public List<Integer> getIngredients_for_search() {
        return ingredients_for_search;
    }

    public void setIngredients_for_search(ArrayList<Integer> ingredients_for_search) {
        this.ingredients_for_search = ingredients_for_search;
    }

    public List<Integer> getLack_of_ingredients_for_search() {
        return lack_of_ingredients_for_search;
    }

    public void setLack_of_ingredients_for_search(ArrayList<Integer> lack_of_ingredients_for_search) {
        this.lack_of_ingredients_for_search = lack_of_ingredients_for_search;
    }

    public String getUser_name_for_search() {
        return user_name_for_search;
    }

    public void setUser_name_for_search(String user_name_for_search) {
        this.user_name_for_search = user_name_for_search;
    }

    public String getRecipe_for_search() {
        return recipe_for_search;
    }

    public void setRecipe_for_search(String recipe_for_search) {
        this.recipe_for_search = recipe_for_search;
    }

    public ArrayList<Chip> getCcategory_for_search() {
        return Ccategory_for_search;
    }

    public void setCcategory_for_search(ArrayList<Chip> ccategory_for_search) {
        Ccategory_for_search = ccategory_for_search;
    }
    public void addcategory_for_search(Integer id) {
        category_for_search.add(id);
    }

    public ArrayList<Chip> getCingredients_for_search() {
        return Cingredients_for_search;
    }

    public void setCingredients_for_search(ArrayList<Chip> cingredients_for_search) {
        Cingredients_for_search = cingredients_for_search;
    }
    public void addingredients_for_search(Integer id) {
        ingredients_for_search.add(id);
    }

    public ArrayList<Chip> getClack_of_ingredients_for_search() {
        return Clack_of_ingredients_for_search;
    }

    public void setClack_of_ingredients_for_search(ArrayList<Chip> clack_of_ingredients_for_search) {
        Clack_of_ingredients_for_search = clack_of_ingredients_for_search;
    }
    public void addlack_of_ingredients_for_search(Integer id) {
        lack_of_ingredients_for_search.add(id);
    }
    public boolean isCategoryChecked(Integer id){
        return category_for_search.contains(id);
    }
    public boolean isIngredientChecked(Integer id){
        return ingredients_for_search.contains(id);
    }
    public boolean isLackOfIngredientChecked(Integer id){
        return lack_of_ingredients_for_search.contains(id);
    }
    public void removeCategory_for_search(Integer id) {
        category_for_search.remove(id);
    }
    public void removeIngredients_for_search(Integer id) {
        ingredients_for_search.remove(id);
    }

    public void removeLack_of_ingredients_for_search(Integer id) {
        lack_of_ingredients_for_search.remove(id);
    }
}
