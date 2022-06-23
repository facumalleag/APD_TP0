package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.services.RecipeService;
import com.example.myapplication.services.UserService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AfterSearchActivity extends AppCompatActivity {

    Fragment fragmentoFiltros;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_search);

        SearchView searchView = findViewById(R.id.search_field);

        fragmentoFiltros = new RecetaFiltroFragment();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService rs = retrofit.create(RecipeService.class);
        Call<JsonElement> call = rs.listRecipeForPresentacion();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //lblEstado.setText(response.body() );
                if (response.isSuccessful()) {
                    //System.out.println(response.body());
                    JsonObject jsonObject = response.body().getAsJsonObject();

                    JsonObject pp = jsonObject.get("data").getAsJsonObject();
                    //System.out.println(pp);
                    JsonArray pp2 = pp.get("recipeFetched").getAsJsonArray();
                    // System.out.println(pp2);
                    //JsonArray pp2 = pp.get("recipesFetched").getAsJsonArray();
                    String ss = "";
                    addCard(pp2);

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
        Intent intent = new Intent(this, AfterSearchActivity.class);
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


    private void addCard(JsonArray names) {
        final View view = getLayoutInflater().inflate(R.layout.card_recipes, null);
        TextView nameView = view.findViewById(R.id.name);
        String ff = "";
        ff = ff+names.get(0).getAsJsonObject().get("name");
        nameView.setText(ff);
        layout.addView(view);
    }

}