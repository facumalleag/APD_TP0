package com.example.myapplication;

import static com.example.myapplication.Constants.ID_RECIPE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.services.CategoryService;
import com.example.myapplication.services.RecipeService;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateFirstRecipeActivity extends AppCompatActivity implements DialogoTitleRecipe.NoticeDialogListener {

    private EditText editTextTitleRecipe;
    private Button btnCreateTitleRecipe;
    private String idRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_recipe_first);

        editTextTitleRecipe = findViewById(R.id.editTextTitleRecipe);
        btnCreateTitleRecipe = findViewById(R.id.btnCreateTitleRecipe);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();


    }

    public void actionCreate(View view){

        String titleRecipe= editTextTitleRecipe.getText().toString().trim();

        if (titleRecipe.isEmpty()){
            editTextTitleRecipe.setError("El titulo de la receta no puede estar vacio.");
            return;
        }

        checkTitleRecipe(titleRecipe);
    }

    public void actionVolver(View view){ 
        Intent intent = new Intent(this, HomeApplicationActivity.class);
        startActivity(intent);
        finish();
    }

    public void mostrarAlerta() {
        DialogFragment newFragment = new DialogoTitleRecipe();
        newFragment.show(getSupportFragmentManager(), "Atención");
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(this, CreateSecondRecipeActivity.class);
        intent.putExtra(Intent.EXTRA_TITLE, editTextTitleRecipe.getText().toString());
        intent.putExtra(ID_RECIPE, idRecipe);
        startActivity(intent);
        finish();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Intent intent = new Intent(this, EditRecipeActivity.class);
        intent.putExtra(ID_RECIPE, idRecipe);
        startActivity(intent);
        finish();

    }

    public void checkTitleRecipe(String title){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService fs = retrofit.create(RecipeService.class);
        Call<JsonElement> call = fs.checkRecipeByName(title);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200){
                        idRecipe = response.body().getAsJsonObject().get("data").getAsJsonObject().get("id").getAsString();
                        mostrarAlerta();
                    } else {
                        checkRecipeSuccesful();
                    }
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

    private void checkRecipeSuccesful() {
        Intent intent = new Intent(this, CreateSecondRecipeActivity.class);
        intent.putExtra(Intent.EXTRA_TITLE, editTextTitleRecipe.getText().toString());
        intent.putExtra(ID_RECIPE, idRecipe);
        startActivity(intent);
        finish();
    }
}
