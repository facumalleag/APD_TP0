package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class CreateFirstRecipeActivity extends AppCompatActivity implements DialogoTitleRecipe.NoticeDialogListener {

    private EditText editTextTitleRecipe;
    private Button btnCreateTitleRecipe;

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

        if (checkTitleRecipe(titleRecipe)){
            Intent intent = new Intent(this, HomeApplicationActivity.class);
            startActivity(intent);
        } else {
            mostrarAlerta();
        }

    }

    public void actionVolver(View view){ 
        Intent intent = new Intent(this, HomeApplicationActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean checkTitleRecipe (String title){
        boolean resultado=false;

        return resultado;

    }



    public void mostrarAlerta() {
        DialogFragment newFragment = new DialogoTitleRecipe();
        newFragment.show(getSupportFragmentManager(), "Atención");
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(this, CreateSecondRecipeActivity.class);
        intent.putExtra(Intent.EXTRA_TITLE, editTextTitleRecipe.getText().toString());
        startActivity(intent);
        finish();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
