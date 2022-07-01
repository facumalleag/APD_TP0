package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.myapplication.adapter.SliderAdapter;
import com.smarteist.autoimageslider.SliderView;

public class HomeApplicationActivity extends AppCompatActivity{

    FragmentContainerView fragmentoFiltros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_application);

        SearchView searchView = findViewById(R.id.search_field);

        //fragmentoFiltros = findViewById(R.id.fragmentContainerView);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarBusquedaActivity(view);
            }
        });



    }


    public void iniciarHome(View view){
        Intent intent = new Intent(this, HomeApplicationActivity.class);
        startActivity(intent);
    }
    public void iniciarBusquedaActivity(View view) {

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
    public void iniciarProfileActivity(View view) {

        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
    public void iniciarFavouriteActivity(View view) {

        Intent intent = new Intent(this, FavouritesRecipesActivity.class);
        startActivity(intent);
    }

    public void actionCreate(View view){
        Intent intent = new Intent(this, CreateFirstRecipeActivity.class);
        startActivity(intent);
        finish();
    }

}