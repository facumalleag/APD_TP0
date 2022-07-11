package com.example.myapplication.services;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MeasurementService {
    @GET("/measurement/list")
    Call<JsonElement> listMeasurement();
}
