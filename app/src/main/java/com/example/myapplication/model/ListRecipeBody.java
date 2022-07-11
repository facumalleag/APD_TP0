package com.example.myapplication.model;

public class ListRecipeBody {
    private Integer id;//user id
    private Integer order;


    public ListRecipeBody(Integer email, Integer order){
        super();
        this.id = email;
        this.order = order;

    }

    public Integer getId(){
        return id;
    }
    public Integer getOrder(){
        return order;
    }


}


