package com.example.myapplication.services;

import com.example.myapplication.model.ListRecipeBody;
import com.example.myapplication.model.user;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RecipeService {

    @GET("/recipe/listRecipeForPresentacion")
    Call<JsonElement> listRecipeForPresentacion();

    //@GET("/recipe/listRecipeByUserId") tuve que usar el codigo de abjo porque se supone que get no puede contener body
    //@HTTP(method = "GET", path = "/recipe/listRecipeByUserId", hasBody = true)
    @GET("/recipe/listRecipeBy/userId/{id}/order/{order}")
    Call<JsonElement> listRecipeByUserId( @Path("id") String id,
                                          @Path("order") String order);
    @GET("/recipe/getBy/{id}")
    Call<JsonElement> getRecipeById( @Path("id") String id);
}
