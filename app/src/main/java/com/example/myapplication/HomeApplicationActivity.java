package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

public class HomeApplicationActivity extends AppCompatActivity{

    Fragment fragmentoFiltros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_application);

        SearchView searchView = findViewById(R.id.search_field);

        fragmentoFiltros = new RecetaFiltroFragment();

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarBusquedaActivity(view);
            }
        });



    }

    private void iniciarBusquedaActivity(View view) {

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }


}