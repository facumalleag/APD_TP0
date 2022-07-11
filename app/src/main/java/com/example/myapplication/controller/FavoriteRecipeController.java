package com.example.myapplication.controller;

import com.example.myapplication.model.Receta;
import com.example.myapplication.model.Usuario;

import java.util.ArrayList;

public class FavoriteRecipeController {


    private static FavoriteRecipeController instancia;
    private ArrayList<Integer> coleccion_id_recetas= new ArrayList<Integer>();

    public static FavoriteRecipeController getInstancia() {
        if (instancia==null) {
            instancia=new FavoriteRecipeController();
        }
        return instancia;
    }

    public void addIdToList(Integer id){
        coleccion_id_recetas.add(id);

    }
    public void getIdByIndex(Integer indx){
        coleccion_id_recetas.get(indx);
    }
    public void removeIdByIndex(Integer indx){
        coleccion_id_recetas.remove(indx);
    }


}


