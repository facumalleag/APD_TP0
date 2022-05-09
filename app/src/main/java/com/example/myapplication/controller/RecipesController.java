package com.example.myapplication.controller;

import com.example.myapplication.model.Receta;

import java.util.ArrayList;

public class RecipesController {

    private ArrayList<Receta> coleccion_recetas= new ArrayList<Receta>();

    private static RecipesController instancia;


    public static RecipesController getInstancia() {
        if (instancia==null) {
            instancia=new RecipesController();
        }
        return instancia;
    }
}
