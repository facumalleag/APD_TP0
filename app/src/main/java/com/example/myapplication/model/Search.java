package com.example.myapplication.model;

import java.util.List;

public class Search {
    private String recipeName;
    private List<Integer> categoryList;
    private String userName;
    private List<Integer> ingredientsList;
    private List<Integer> lackOfIngredientsList;
    private Integer order;
    private String orderAtr;


    public Search(String recipeName, List<Integer> categoryList, String userName, List<Integer> ingredientsList, List<Integer> lackOfIngredientsList, Integer order,String orderAtr){
        super();
        this.recipeName = recipeName;
        this.categoryList = categoryList;
        this.userName = userName;
        this.ingredientsList = ingredientsList;
        this.lackOfIngredientsList = lackOfIngredientsList;
        this.order = order;
        this.orderAtr = orderAtr;
    }


}