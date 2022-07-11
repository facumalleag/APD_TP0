package com.example.myapplication.services;

import com.example.myapplication.model.Receta;
import com.example.myapplication.model.Search;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
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

    @HTTP(method = "GET", path = "/recipe/check/name/{name}")
    Call<JsonElement> checkRecipeByName( @Path("name") String name);


    @POST("/recipe/create")
    Call<JsonElement> createRecipe(@Body Receta body);

    @DELETE("/recipe/deleteRecipeBy/recipeId/{id}")
    Call<JsonElement> deleteRecipe(@Path("id") String id);

    @POST("/recipe/search")
    Call<JsonElement> searchRecipe(@Body Search body);

}
