package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.myapplication.controller.FavoriteRecipeController;
import com.example.myapplication.controller.UserController;
import com.example.myapplication.services.FavoriteService;
import com.example.myapplication.services.RecipeService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavouritesRecipesActivity extends AppCompatActivity {

    AlertDialog dialog;
    Fragment fragmentoFiltros;
    LinearLayout layout;
    Integer favoriteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_profile_layout);
        TextView EmptyListTextView = findViewById(R.id.EmptyListTextView);
        EmptyListTextView.setVisibility(View.INVISIBLE);
        //SearchView searchView = findViewById(R.id.search_field);

        buildDialog();

        layout=findViewById(R.id.container);
        findViewById(R.id.loadingPanel).bringToFront();
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        getFavoriteRecipe();
        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);



    }
    public void getFavoriteRecipe(){
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
                    System.out.println("");
                    createCards(response.body().getAsJsonObject().get("listedFavorites").getAsJsonArray());


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
        String nameRecipe, rating,time,userName,recipeId;
        Integer  favoriteId;
        if (listOfRecipes.size() ==0){
            TextView EmptyListTextView = findViewById(R.id.EmptyListTextView);
            EmptyListTextView.setVisibility(View.VISIBLE);
            return;
        }
        for (int i = 0; i < listOfRecipes.size(); i++) {
            favoriteId = listOfRecipes.get(i).getAsJsonObject().get("favoriteId").getAsInt() ;
            recipeId= listOfRecipes.get(i).getAsJsonObject().get("recipeId").getAsString() ;
            nameRecipe = listOfRecipes.get(i).getAsJsonObject().get("name").getAsString();
            rating = listOfRecipes.get(i).getAsJsonObject().get("totalRating").getAsString();
            time = listOfRecipes.get(i).getAsJsonObject().get("time").getAsString();
            userName = listOfRecipes.get(i).getAsJsonObject().get("userName").getAsString();
            addCard(recipeId,favoriteId,nameRecipe,rating,time,userName);
        }
    }
    private void addCard(String recipeId,Integer favoriteId, String nameRecipe,String rating,String time,String userName) {
        final View view = getLayoutInflater().inflate(R.layout.material_io_card_favorite_rescipe, null);
        TextView ratingView = view.findViewById(R.id.rating);
        ratingView.setText(rating);
        TextView recipeTitleView = view.findViewById(R.id.recipeTitle);
        recipeTitleView.setText(nameRecipe);
        TextView UserNameView = view.findViewById(R.id.userName);
        UserNameView.setText("Por "+userName);
        TextView timeView = view.findViewById(R.id.time);
        timeView.setText(time);
        ImageView heartImage = view.findViewById(R.id.heart);
        heartImage.setClickable(true);
        heartImage.bringToFront();
        heartImage.setOnClickListener(v -> showDialog2(favoriteId));
        view.setId(favoriteId);
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
    private void showDialog2(int id){
        this.favoriteId = id;
        dialog.show();

    }
    private void buildDialog() {
        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        builder.setView(view);
        builder.setMessage("¿Desea eliminar de la lista de favoritos la receta?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeFavorite(favoriteId);
                        getFavoriteRecipe();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog = builder.create();
    }

    private void removeFavorite(Integer favoriteId){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FavoriteService fs = retrofit.create(FavoriteService.class);
        Call<JsonElement> call = fs.deleteFavoriteRecipeById(Integer.toString(favoriteId));

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //lblEstado.setText(response.body() );
                if (response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplication().getApplicationContext(), "Recete eliminada de lista de favoritos", Toast.LENGTH_SHORT);
                    toast.show();
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

}

