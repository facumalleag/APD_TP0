package com.example.myapplication;

import static com.example.myapplication.Constants.BASE_URL;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.controller.RecipesController;
import com.example.myapplication.model.Search;
import com.example.myapplication.services.FavoriteService;
import com.example.myapplication.services.RecipeService;
import com.example.myapplication.services.UserService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AfterSearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Fragment fragmentoFiltros;
    LinearLayout layout;
    Integer order;
    private RecipesController RecpCont;
    private JsonArray listOfFavorites;
    AlertDialog dialog;
    Integer favoriteId;
    String[] options = { "Ordenar por", "Fecha de creación",
            "Nombre de usuario", "Nombre de Receta"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_search);

        RecpCont = RecipesController.getInstancia();
        ProgressBar searchView = findViewById(R.id.progress_bar_after_search);
        TextView EmptyListTextView = findViewById(R.id.EmptyListTextViewAfterSearch);
        EmptyListTextView.setVisibility(View.INVISIBLE);
        layout=findViewById(R.id.containerAfterSearch);
        getFavoriteRecipe();
        order = 0;

        searchRecipes("0","name");

        searchView.setVisibility(View.INVISIBLE);
        Spinner spino = findViewById(R.id.orderSpinner);
        spino.setOnItemSelectedListener(this);

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                options);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spino.setAdapter(ad);


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

    private void searchRecipes(String ord,String ordrAtr){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService rs = retrofit.create(RecipeService.class);
        String recipeSearch = RecpCont.getRecipe_for_search();
        String usrName = RecpCont.getUser_name_for_search();
        List cat = RecpCont.getCategory_for_search();
        List ingrList = RecpCont.getIngredients_for_search();
        List ingrLackList = RecpCont.getLack_of_ingredients_for_search();
        Search srch = new Search( recipeSearch,  cat,  usrName,  ingrList,  ingrLackList,  ord,ordrAtr);

        Call<JsonElement> call = rs.searchRecipe(srch);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //lblEstado.setText(response.body() );
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    searchRecipesOnAfterFetch(response.body().getAsJsonObject().get("data").getAsJsonObject().get("recipeFetched").getAsJsonArray());

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
    private void searchRecipesOnAfterFetch(JsonArray listOfRecipes){
        String nameRecipe, rating,time,userName,recipeId;
        Integer  favoriteId;
        if (listOfRecipes.size() ==0){
            TextView EmptyListTextView = findViewById(R.id.EmptyListTextViewAfterSearch);
            EmptyListTextView.setVisibility(View.VISIBLE);
            return;
        }
        for (int i = 0; i < listOfRecipes.size(); i++) {

            recipeId= listOfRecipes.get(i).getAsJsonObject().get("id").getAsString() ;
            nameRecipe = listOfRecipes.get(i).getAsJsonObject().get("name").getAsString();
            rating = listOfRecipes.get(i).getAsJsonObject().get("totalRating").getAsString();
            time = listOfRecipes.get(i).getAsJsonObject().get("time").getAsString();
            userName = listOfRecipes.get(i).getAsJsonObject().get("user").getAsJsonObject().get("name").getAsString();
            addCard(recipeId,nameRecipe,rating,time,userName);
        }
    }
    private void addCard(String recipeId, String nameRecipe,String rating,String time,String userName) {
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
        if (hasRecipeInFavorites(recipeId)){

            heartImage.setImageResource(R.drawable.baseline_favorite_24);
        }else{

            heartImage.setImageResource(R.drawable.ic_baseline_heart_blue_24);

        }
        view.setId(Integer.parseInt(recipeId));
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





    public void getFavoriteRecipe(){
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

                    //JsonArray recipesAsJsonArray = response.body().getAsJsonObject().get("data").getAsJsonObject().get("recipeFetched").getAsJsonArray();
                    System.out.println("");
                    listOfFavorites = response.body().getAsJsonObject().get("listedFavorites").getAsJsonArray();


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
    private boolean hasRecipeInFavorites(String recipeId){

        boolean isFavorite =false;
        System.out.println(listOfFavorites);
        for (int i = 0; i < listOfFavorites.size(); i++) {
            if(listOfFavorites.get(i).getAsJsonObject().get("recipeId").getAsString().equals(recipeId) ) {
                isFavorite = true;

            }
        }
        return isFavorite;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ScrollView scrollv = findViewById(R.id.scrollViewAfterSearch);


        if(i!=0){layout.removeAllViews();}

        if(i==1){searchRecipes("0","createdAt");}
        if(i==2){searchRecipes("name","user");}
        if(i==3){searchRecipes("0","name");}



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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