package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.services.FavoriteService;
import com.example.myapplication.services.RecipeService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowRecipeActivity extends AppCompatActivity {

    AlertDialog dialog;
    Fragment fragmentoFiltros;
    LinearLayout layoutIngredients;
    LinearLayout layoutSteps;
    Integer favoriteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe);

        //SearchView searchView = findViewById(R.id.search_field);

        layoutIngredients=findViewById(R.id.containerIngredients);
        layoutSteps=findViewById(R.id.containerSteps);

        //findViewById(R.id.loadingPanel).bringToFront();
        //findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        getRecipe();
        //findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);



    }
    public void getRecipe(){
        fragmentoFiltros = new RecetaFiltroFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService rs = retrofit.create(RecipeService.class);

        Call<JsonElement> call = rs.getRecipeById( Integer.toString(1));
        //OJO el id de la receta esta hardcodeado
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //lblEstado.setText(response.body() );
                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    //JsonArray recipesAsJsonArray = response.body().getAsJsonObject().get("data").getAsJsonObject().get("recipeFetched").getAsJsonArray();
                    System.out.println(response.body().getAsJsonObject().get("data").getAsJsonObject().get("stepsList"));
                    //createCards(response.body().getAsJsonObject().get("listedFavorites").getAsJsonArray());
                    showData(response.body().getAsJsonObject().get("data").getAsJsonObject());

                } else {
                    if (response.code() == 400) {
                        Toast toast = Toast.makeText(getApplication().getApplicationContext(), "Ocurrio un error, intente mas tarde", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }



    public void actionCreate(View view){
        Intent intent = new Intent(this, CreateFirstRecipeActivity.class);
        startActivity(intent);
        finish();
    }
    private void showData(JsonObject data) {

        JsonObject recipeFetched= data.get("recipeFetched").getAsJsonObject();
        TextView nameTitleView = findViewById(R.id.txtViewTituloDeReceta);
        nameTitleView.setText(recipeFetched.get("name").getAsString());
        TextView ratingView = findViewById(R.id.rating);
        ratingView.setText(recipeFetched.get("totalRating").getAsString());
        TextView timeView = findViewById(R.id.time);
        timeView.setText(recipeFetched.get("time").getAsString());
        TextView userNameView = findViewById(R.id.userName);
        userNameView.setText(recipeFetched.get("user").getAsJsonObject().get("name").getAsString());
        TextView descriptionView = findViewById(R.id.txtViewCantidadPorcion);
        descriptionView.setText(recipeFetched.get("description").getAsString());
        TextView cantidadIngredientesView = findViewById(R.id.txtViewCantidadIngredientes);
        TextView txtViewCantidadPorcionView = findViewById(R.id.txtViewCantidadPorcion);
        txtViewCantidadPorcionView.setText(recipeFetched.get("serving").getAsString());
        processIngredients(data.get("ingredientsList").getAsJsonArray(),cantidadIngredientesView);
        processSteps(data.get("stepsList").getAsJsonArray());


    }
    private void processIngredients(JsonArray ingredientsList, TextView txtViewCantidadIngredientes) {
        String nameIngredient, quantity,measurement;
        txtViewCantidadIngredientes.setText(String. valueOf(ingredientsList.size())+ " items");
        for (int i = 0; i < ingredientsList.size(); i++) {
            nameIngredient = ingredientsList.get(i).getAsJsonObject().get("ingredients").getAsJsonObject().get("description").getAsString() ;
            quantity= ingredientsList.get(i).getAsJsonObject().get("cantidad").getAsString() ;
            measurement = ingredientsList.get(i).getAsJsonObject().get("measurement").getAsJsonObject().get("description").getAsString();
            addIngredientCard(nameIngredient, quantity,measurement);
        }

    }

    private void addIngredientCard(String nameIngredient,String quantity,String measurement){
        final View view = getLayoutInflater().inflate(R.layout.material_io_card_ingredient, null);
        TextView ingredientNameView = view.findViewById(R.id.ingredientName);
        ingredientNameView.setText(nameIngredient);
        TextView ingredientNumberView = view.findViewById(R.id.ingredientNumber);
        ingredientNumberView.setText(quantity);
        TextView ingredientMeasurementView = view.findViewById(R.id.ingredientMeasurement);
        ingredientMeasurementView.setText(measurement);
        layoutIngredients.addView(view);
    }
    private void processSteps(JsonArray ingredientsList) {
        String stepNumber, description;
        for (int i = 0; i < ingredientsList.size(); i++) {
            stepNumber = ingredientsList.get(i).getAsJsonObject().get("stepOrder").getAsString() ;
            description= ingredientsList.get(i).getAsJsonObject().get("description").getAsString() ;
            addStepCard(stepNumber, description);
        }

    }
    private void addStepCard(String stepNumber,String description){
        final View view = getLayoutInflater().inflate(R.layout.material_io_card_steps, null);
        TextView stepOrderView = view.findViewById(R.id.stepOrder);
        stepOrderView.setText(stepNumber);
        TextView stepDescriptionView = view.findViewById(R.id.stepDescription);
        stepDescriptionView.setText(description);
        layoutSteps.addView(view);
    }
    private void showDialog2(int id){
        this.favoriteId = id;
        dialog.show();

    }




}

