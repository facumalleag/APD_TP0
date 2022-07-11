package com.example.myapplication.services;

import com.example.myapplication.model.User;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @FormUrlEncoded
    @POST("/login")
    Call<JsonElement> login(
            @Field("email") String username,
            @Field("password") String password);

    @POST("/halfRegister")
    Call<JsonElement> halfRegister(@Body User body);

    @POST("/fullRegister")
    Call<JsonElement> fullRegister(@Body User body);

    @GET("/verify/Email/{email}/Alias/{alias}")
    Call<JsonElement> verifyEmailAndAlias(
            @Path("email") String email,
            @Path("alias") String alias);

    @GET("/checkUserBy/email/{email}/")
    Call<JsonElement> verifyEmail(
            @Path("email") String email);

    @FormUrlEncoded
    @PUT("/updatePassword")
    Call<JsonElement> updatePassword(
            @Field("email") String username,
            @Field("password") String password);
}
