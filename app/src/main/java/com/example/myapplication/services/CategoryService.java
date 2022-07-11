package com.example.myapplication.services;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("/category/list")
    Call<JsonElement> listCategories();

}
