package com.example.myapplication;

import static com.example.myapplication.Constants.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.controller.UserController;
import com.example.myapplication.model.ListRecipeBody;
import com.example.myapplication.services.RecipeService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfileActivity extends AppCompatActivity {

    Fragment fragmentoFiltros;
    LinearLayout layout;
    ImageView imageViewUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_layout);
        TextView EmptyListTextView = findViewById(R.id.EmptyListTextView);
        EmptyListTextView.setVisibility(View.INVISIBLE);
        //SearchView searchView = findViewById(R.id.search_field);
        TextView subtitleView = findViewById(R.id.userNameView);
        subtitleView.setText(UserController.getInstancia().getName());
        imageViewUserProfile = (ImageView) findViewById(R.id.imageViewUserProfile);
        imageViewUserProfile.setImageResource(R.drawable.avatar);
        layout=findViewById(R.id.container);
        fragmentoFiltros = new RecetaFiltroFragment();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService rs = retrofit.create(RecipeService.class);
        //Call<JsonElement> call = rs.listRecipeByUserId( Integer.toString(UserController.getInstancia().getUserId()),Integer.toString(0));
        Call<JsonElement> call = rs.listRecipeByUserId( Integer.toString(UserController.getInstancia().getUserId()),Integer.toString(0));

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //lblEstado.setText(response.body() );
                if (response.isSuccessful()) {
                    //System.out.println(response.body());
                    JsonArray recipesAsJsonArray = response.body().getAsJsonObject().get("data").getAsJsonObject().get("recipeFetched").getAsJsonArray();
                    createCards(recipesAsJsonArray);

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
        String recipeId,nameRecipe, descriptionRecipe,time,totalRating, stepsrecipe,status;
        if (listOfRecipes.size() ==0){
            TextView EmptyListTextView = findViewById(R.id.EmptyListTextView);
            EmptyListTextView.setVisibility(View.VISIBLE);
            return;
        }
        for (int i = 0; i < listOfRecipes.size(); i++) {
            System.out.println("acaaa");
            System.out.println(listOfRecipes.get(i).getAsJsonObject());
            recipeId = listOfRecipes.get(i).getAsJsonObject().get("id").getAsString();
            nameRecipe = listOfRecipes.get(i).getAsJsonObject().get("name").getAsString();
            descriptionRecipe = listOfRecipes.get(i).getAsJsonObject().get("description").getAsString();
            time = listOfRecipes.get(i).getAsJsonObject().get("time").getAsString();
            totalRating = listOfRecipes.get(i).getAsJsonObject().get("totalRating").getAsString();
            stepsrecipe = listOfRecipes.get(i).getAsJsonObject().get("totalSteps").getAsString();
            status = listOfRecipes.get(i).getAsJsonObject().get("status").getAsJsonObject().get("description").getAsString();
            //JsonObject status1 = listOfRecipes.get(i).getAsJsonObject();

            addCard(recipeId,nameRecipe,descriptionRecipe,time,totalRating,stepsrecipe,status);
        }
    }
    private void addCard(String recipeId,String nameRecipe,String descriptionRecipe,String time,String totalRating,String stepsrecipe,String status) {

        final View view = getLayoutInflater().inflate(R.layout.material_io_card_porfile_rescipe, null);
        TextView ratingView = view.findViewById(R.id.rating1);
        ratingView.setText(totalRating);
        TextView recipeTitleView = view.findViewById(R.id.recipeTitle2);
        recipeTitleView.setText(nameRecipe);
        TextView UserNameView = view.findViewById(R.id.inredientesTime);
        UserNameView.setText(stepsrecipe + " ingredientes | " +time+" minutos");
        TextView timeView = view.findViewById(R.id.recipeStatus);
        timeView.setText(status);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShowRecipeActivity.class);
                intent.putExtra("key",recipeId);
                System.out.println(recipeId);
                startActivity(intent);
            }
        });
        layout.addView(view);



    }

    public void iniciarProfileActivity(View view) {

        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
    public void iniciarFavouriteActivity(View view) {

        //Intent intent = new Intent(this, FavouritesRecipesActivity.class);
        Intent intent = new Intent(this, FavouritesRecipesActivity.class);
        startActivity(intent);
    }

}

