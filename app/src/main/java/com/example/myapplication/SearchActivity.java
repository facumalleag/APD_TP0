package com.example.myapplication;

import static com.example.myapplication.Constants.BASE_URL;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.controller.RecipesController;
import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.ui.main.SectionsPagerAdapter;
import com.example.myapplication.databinding.ActivitySearchBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.myapplication.services.CategoryService;
import com.example.myapplication.services.IngredientService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private RecipesController RecpCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        RecpCont = RecipesController.getInstancia();

        SearchView sv = findViewById(R.id.SearchViewRecipe);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RecpCont.setRecipe_for_search(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
                RecpCont.setRecipe_for_search(newText);
                //}
                return true;
            }

            public void callSearch(String query) {
                System.out.println(query);
            }

        });

    }
    public void search(View v) {

        System.out.println("RECIPE"+RecpCont.getRecipe_for_search());
        System.out.println("USER"+RecpCont.getUser_name_for_search());
        System.out.println("Category"+RecpCont.getCategory_for_search());
        System.out.println("Ingredients"+RecpCont.getIngredients_for_search());
        System.out.println("LackIngredients"+RecpCont.getLack_of_ingredients_for_search());
        Intent intent = new Intent(this, AfterSearchActivity.class);
        startActivity(intent);

    }
    public void getCategories(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CategoryService cs = retrofit.create(CategoryService.class);
        Call<JsonElement> call = cs.listCategories();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    System.out.println("");
                    createCategoryChips (response.body().getAsJsonObject().get("listedCategories").getAsJsonArray());


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
    public void getIngredient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IngredientService ings = retrofit.create(IngredientService.class);
        Call<JsonElement> call = ings.listIngredient();
         call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    System.out.println("");
                    createIngredientChips(response.body().getAsJsonObject().get("listedIngredients").getAsJsonArray());
                    //createIngredientChips2(response.body().getAsJsonObject().get("listedIngredients").getAsJsonArray());
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

    /**
     *
     * Este seria el lugar ideal para buscar los datos pero no lo hice funcionar.
     * Los datos se cargan en Placeholder fragment
     */
    public void createCategoryChips (JsonArray listCategories){

        for(JsonElement category : listCategories){
            int id = category.getAsJsonObject().get("id").getAsInt();
            String name = category.getAsJsonObject().get("description").getAsString();
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout, null);
            chip.setText(name);
            chip.setId(id);

            chip.isCheckable();

            //RecipesController.getInstancia().addChipCcategory_for_search(chip);
        }
    }
    public void createIngredientChips (JsonArray listCategories){


        for(JsonElement category : listCategories){
            int id = category.getAsJsonObject().get("id").getAsInt();
            String name = category.getAsJsonObject().get("description").getAsString();
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout, null);
            chip.setText(name);
            chip.setId(id);
            //RecipesController.getInstancia().addChipingredients_for_search(chip);
            //RecipesController.getInstancia().addChipClack_of_ingredients_for_search(chip);
        }

    }
    public void createIngredientChips2 (JsonArray listCategories){


        for(JsonElement category : listCategories){
            int id = category.getAsJsonObject().get("id").getAsInt();
            String name = category.getAsJsonObject().get("description").getAsString();
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout, null);
            chip.setText(name);
            chip.setId(id);
           // RecipesController.getInstancia().addChipClack_of_ingredients_for_search(chip);
        }

    }
}