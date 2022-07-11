package com.example.myapplication;

import static com.example.myapplication.Constants.BASE_URL;

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

import com.example.myapplication.model.favorite;
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
    String favoriteId;
    String recipeId;
    boolean isFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe);
        Bundle extras = getIntent().getExtras();
        buildDialog();
        if (extras != null) {
            recipeId = extras.getString("key");
            //The key argument here must match that used in the other activity
        }
        System.out.println("aca: "+recipeId);
        //SearchView searchView = findViewById(R.id.search_field);
        this.isFavorite = false;
        layoutIngredients=findViewById(R.id.containerIngredients);
        layoutSteps=findViewById(R.id.containerSteps);

        //findViewById(R.id.loadingPanel).bringToFront();
        //findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        getRecipe();
        //findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
        ImageView fav= findViewById(R.id.favoriteRecipe);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrRemoveFromFavorite();
            }
        });


    }
    public void getRecipe(){
        fragmentoFiltros = new RecetaFiltroFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService rs = retrofit.create(RecipeService.class);

        Call<JsonElement> call = rs.getRecipeById(recipeId);
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
        timeView.setText(recipeFetched.get("time").getAsString() + " min");

        TextView userNameView = findViewById(R.id.userName);
        userNameView.setText(recipeFetched.get("user").getAsJsonObject().get("name").getAsString());
        TextView descriptionView = findViewById(R.id.description);
        descriptionView.setText(recipeFetched.get("description").getAsString());
        TextView cantidadIngredientesView = findViewById(R.id.txtViewCantidadIngredientes);
        TextView txtViewCantidadPorcionView = findViewById(R.id.txtViewCantidadPorcion);
        txtViewCantidadPorcionView.setText(recipeFetched.get("serving").getAsString());
        processIngredients(data.get("ingredientsList").getAsJsonArray(),cantidadIngredientesView);
        processSteps(data.get("stepsList").getAsJsonArray());
        getFavoritesByUserId();


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
    private void showDialog2(){
        dialog.show();
    }

    private void getFavoritesByUserId(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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
                    hasRecipeInFavorites(response.body().getAsJsonObject().get("listedFavorites").getAsJsonArray());

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
    private void hasRecipeInFavorites(JsonArray favoritesList){
        for (int i = 0; i < favoritesList.size(); i++) {
            if(favoritesList.get(i).getAsJsonObject().get("recipeId").getAsString().equals(recipeId) ) {
                isFavorite = true;
                favoriteId = favoritesList.get(i).getAsJsonObject().get("favoriteId").getAsString();
            }
        }
        changeFavoriteIcon(isFavorite);
    }
    private void changeFavoriteIcon(boolean isFavorite){
        if (isFavorite){
            ImageView favoriteRecipe = findViewById(R.id.favoriteRecipe);
            favoriteRecipe.setImageResource(R.drawable.baseline_favorite_24);
        }else{
            ImageView favoriteRecipe = findViewById(R.id.favoriteRecipe);
            favoriteRecipe.setImageResource(R.drawable.ic_baseline_heart_blue_24);
            favoriteId = "";
            this.isFavorite=false;
        }
    }

    public void addOrRemoveFromFavorite(){
        if (isFavorite){
            showDialog2();
        }else{

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            FavoriteService fs = retrofit.create(FavoriteService.class);
            //favorite newFavorite = new favorite(recipeId,  Integer.toString(UserController.getInstancia().getUserId()),"...");
            favorite newFavorite = new favorite(recipeId, Integer.toString(1),"...");
            Call<JsonElement> call = fs.createFavorite( newFavorite);

            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                    if (response.isSuccessful()) {
                        System.out.println(response.body());
                        Toast toast = Toast.makeText(getApplication().getApplicationContext(), "Se añadio esta receta a su listado de favoritos", Toast.LENGTH_SHORT);
                        toast.show();
                        changeFavoriteIcon(true);

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
    private void buildDialog() {
        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        builder.setView(view);
        builder.setMessage("¿Desea eliminar de la lista de favoritos la receta?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeFavorite(favoriteId);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog = builder.create();
    }

    private void removeFavorite(String favoriteId){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FavoriteService fs = retrofit.create(FavoriteService.class);
        Call<JsonElement> call = fs.deleteFavoriteRecipeById(favoriteId);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //lblEstado.setText(response.body() );
                if (response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplication().getApplicationContext(), "Recete eliminada de lista de favoritos", Toast.LENGTH_SHORT);
                    toast.show();
                    changeFavoriteIcon(false);
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

