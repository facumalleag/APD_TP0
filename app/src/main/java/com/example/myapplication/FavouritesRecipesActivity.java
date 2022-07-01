package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.controller.UserController;
import com.example.myapplication.services.FavoriteService;
import com.example.myapplication.services.RecipeService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavouritesRecipesActivity extends AppCompatActivity {

    Fragment fragmentoFiltros;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_profile_layout);
        TextView EmptyListTextView = findViewById(R.id.EmptyListTextView);
        EmptyListTextView.setVisibility(View.INVISIBLE);
        //SearchView searchView = findViewById(R.id.search_field);


        layout=findViewById(R.id.container);
        fragmentoFiltros = new RecetaFiltroFragment();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FavoriteService fs = retrofit.create(FavoriteService.class);
        //Call<JsonElement> call = fs.listFavoriteRecipesByUserId( Integer.toString(UserController.getInstancia().getUserId()));
        Call<JsonElement> call = fs.listFavoriteRecipesByUserId( Integer.toString(1));
        //OJO el id del usuario esta hardcodeado
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //lblEstado.setText(response.body() );
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    //JsonArray recipesAsJsonArray = response.body().getAsJsonObject().get("data").getAsJsonObject().get("recipeFetched").getAsJsonArray();
                    //createCards(recipesAsJsonArray);
                    addCard2();
                    addCard2();

                } else {
                    if (response.code() == 400) {
                        Toast toast = Toast.makeText(getApplication().getApplicationContext(), "Correo o contraseña invalida", Toast.LENGTH_SHORT);
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

    public void iniciarHome(View view) {
        Intent intent = new Intent(this, HomeApplicationActivity.class);
        startActivity(intent);
    }

    public void iniciarBusquedaActivity(View view) {

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }



    public void actionCreate(View view){
        Intent intent = new Intent(this, CreateFirstRecipeActivity.class);
        startActivity(intent);
        finish();
    }
    private void createCards(JsonArray listOfRecipes) {
        String nameRecipe, descriptionRecipe,time,totalRating, stepsrecipe,status;
        if (listOfRecipes.size() ==0){
            TextView EmptyListTextView = findViewById(R.id.EmptyListTextView);
            EmptyListTextView.setVisibility(View.VISIBLE);
            return;
        }
        for (int i = 0; i < listOfRecipes.size(); i++) {
            nameRecipe = listOfRecipes.get(i).getAsJsonObject().get("name").getAsString();
            descriptionRecipe = listOfRecipes.get(i).getAsJsonObject().get("description").getAsString();
            time = listOfRecipes.get(i).getAsJsonObject().get("time").getAsString();
            totalRating = listOfRecipes.get(i).getAsJsonObject().get("totalRating").getAsString();
            stepsrecipe = listOfRecipes.get(i).getAsJsonObject().get("totalSteps").getAsString();
            status = listOfRecipes.get(i).getAsJsonObject().get("status").getAsJsonObject().get("description").getAsString();
            //JsonObject status1 = listOfRecipes.get(i).getAsJsonObject();
            System.out.println("acaaa"+status);
            addCard(nameRecipe,descriptionRecipe,time,totalRating,stepsrecipe,status);
        }
    }
    private void addCard(String nameRecipe,String descriptionRecipe,String time,String totalRating,String stepsrecipe,String status) {
        final View view = getLayoutInflater().inflate(R.layout.material_io_card_favorite_rescipe, null);
        TextView ratingView = view.findViewById(R.id.rating);
        ratingView.setText("5,0");
        TextView recipeTitleView = view.findViewById(R.id.description_card);
        recipeTitleView.setText("Shawarma");
        TextView UserNameView = view.findViewById(R.id.UserName);
        UserNameView.setText("Por Pepe");
        TextView timeView = view.findViewById(R.id.time);
        timeView.setText("15:20");
        layout.addView(view);

    }
    private void addCard2() {
        final View view = getLayoutInflater().inflate(R.layout.material_io_card_favorite_rescipe, null);
        TextView ratingView = view.findViewById(R.id.rating);
        ratingView.setText("5,0");
        TextView recipeTitleView = view.findViewById(R.id.recipeTitle);
        recipeTitleView.setText("Shawarma");
        TextView UserNameView = view.findViewById(R.id.UserName);
        UserNameView.setText("Por Pepe");
        TextView timeView = view.findViewById(R.id.time);
        timeView.setText("15:20");
        layout.addView(view);

    }

}

