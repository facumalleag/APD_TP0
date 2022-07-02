package com.example.myapplication.services;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FavoriteService {

    @GET("/favorites/listBy/userId/{id}")
    Call<JsonElement> listFavoriteRecipesByUserId( @Path("id") String id);

    //@GET("/recipe/listRecipeByUserId") tuve que usar el codigo de abjo porque se supone que get no puede contener body
    //@HTTP(method = "GET", path = "/recipe/listRecipeByUserId", hasBody = true)
    @DELETE("/favorites/delete/{id}")
    Call<JsonElement> deleteFavoriteRecipeById( @Path("id") String id);
}
