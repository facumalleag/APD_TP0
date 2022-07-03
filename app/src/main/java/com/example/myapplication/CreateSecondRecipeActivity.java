package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.model.Ingrediente;
import com.example.myapplication.services.CategoryService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateSecondRecipeActivity extends AppCompatActivity implements DialogoAgregarIngrediente.NoticeDialogListener{

    private EditText editTextTitleRecipe;
    private TextView txtViewCantidadPorcion, txtViewCantidadTiempo;
    private ImageButton btnAgregarPorcion, btnAgregarTiempo, btnChooseImage;
    private ImageButton btnEliminarPorcion, btnEliminarTiempo;
    private TextView txtViewPorcion, txtViewTiempo;
    private List<Ingrediente> ingredientes = new ArrayList<>();
    public static final int GET_FROM_GALLERY = 3;
    ImageView previewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_recipe_second);
        editTextTitleRecipe = findViewById(R.id.editTextTitle);
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String title = parametros.getString(Intent.EXTRA_TITLE);
            editTextTitleRecipe.setText(title);
            editTextTitleRecipe.setEnabled(false);
        }

        btnAgregarPorcion = findViewById(R.id.btnAgregarPorcion);
        btnEliminarPorcion = findViewById(R.id.btnEliminarPorcion);
        btnAgregarTiempo = findViewById(R.id.btnAgregarTiempo);
        btnEliminarTiempo = findViewById(R.id.btnEliminarTiempo);
        txtViewCantidadPorcion = findViewById(R.id.txtViewCantidadPorcion);
        txtViewPorcion = findViewById(R.id.txtViewPorcion);
        txtViewCantidadTiempo = findViewById(R.id.txtViewCantidadTiempo);
        txtViewTiempo = findViewById(R.id.txtViewTiempo);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        getCategories();
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();


    }

    public void getCategories(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CategoryService fs = retrofit.create(CategoryService.class);
        Call<JsonElement> call = fs.listCategories();
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    System.out.println("");
                    agregarCategorias(response.body().getAsJsonObject().get("listedCategories").getAsJsonArray());


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


    private void agregarCategorias(JsonArray listOfCategories) {
        LinearLayout btnsContainer = findViewById(R.id.container_categories);
        ChipGroup chipGroup = new ChipGroup(this);
        chipGroup.setSingleSelection(false);

        for(JsonElement category : listOfCategories){
            int id = category.getAsJsonObject().get("id").getAsInt();
            String name = category.getAsJsonObject().get("description").getAsString();
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout, chipGroup, false);
            chip.setText(name);
            chip.setId(id);
            chipGroup.addView(chip);
        }
        btnsContainer.addView(chipGroup);
    }

    public void agregarPorcion(View view){
        Integer porciones = Integer.valueOf(txtViewCantidadPorcion.getText().toString());
        porciones++;
        if (porciones >= 2) {
            btnEliminarPorcion.setEnabled(true);
        }
        txtViewCantidadPorcion.setText(porciones.toString());
    }

    public void eliminarPorcion(View view){
        Integer porciones = Integer.valueOf(txtViewCantidadPorcion.getText().toString());
        porciones--;
        if (porciones == 1) {
            btnEliminarPorcion.setEnabled(false);
            txtViewPorcion.setText(R.string.porcion);
        }
        txtViewCantidadPorcion.setText(porciones.toString());
    }

    public void actionCreate(View view){

    }

    public void eliminarIngrediente(View view){

    }

    public void actionVolver(View view){ 
        Intent intent = new Intent(this, HomeApplicationActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Dialog dialogo = dialog.getDialog();
        EditText txtCantIngrediente = dialogo.findViewById(R.id.cantidadIngrediente);
        EditText txtNombreIngrediente = dialogo.findViewById(R.id.nombreIngrediente);
        Spinner txtMedida = dialogo.findViewById(R.id.spinner_cant);
        Integer cantidadIngrediente = 0;
        String nombreIngrediente= txtNombreIngrediente.getText().toString().trim();
        String medida= txtMedida.getSelectedItem().toString();

        if (nombreIngrediente == null || nombreIngrediente.isEmpty()){
            txtNombreIngrediente.setError("El ingrediente no puede estar vacio");
        }

        if (txtCantIngrediente.getText().toString().trim() == null || txtCantIngrediente.getText().toString().isEmpty()){
            txtNombreIngrediente.setError("La cantidad debe ser mayor a cero");
        } else {
            cantidadIngrediente = Integer.valueOf(txtCantIngrediente.getText().toString().trim());
        }

        Ingrediente ingrediente = new Ingrediente(nombreIngrediente, cantidadIngrediente, medida);
        ingredientes.add(ingrediente);

        LinearLayout btnsContainer = findViewById(R.id.container_ingredientes);
        View buttonContainer =  LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_ingrediente, null);
        ImageButton btnEliminarIngrediente = (ImageButton) buttonContainer.findViewById(R.id.btnEliminarIngrediente);
        TextView txtViewIngrediente = (TextView) buttonContainer.findViewById(R.id.txtViewIngrediente);
        txtViewIngrediente.setText(ingrediente.getNombre());
        TextView txtViewCantidad = (TextView) buttonContainer.findViewById(R.id.txtViewCantidad);
        txtViewCantidad.setText(ingrediente.getCantidad() + " " +  ingrediente.getMedida());

        btnsContainer.addView(buttonContainer);

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void actionAgregarIngrediente(View view){
        DialogFragment newFragment = new DialogoAgregarIngrediente();
        newFragment.show(getSupportFragmentManager(), "Atención");
    }

    public void agregarTiempo(View view){
        Integer tiempo = Integer.valueOf(txtViewCantidadTiempo.getText().toString());
        tiempo++;
        if (tiempo >= 2) {
            btnEliminarTiempo.setEnabled(true);
        }
        txtViewCantidadTiempo.setText(tiempo.toString());
    }

    public void eliminarTiempo(View view){
        Integer tiempo = Integer.valueOf(txtViewCantidadTiempo.getText().toString());
        tiempo--;
        if (tiempo == 1) {
            btnEliminarTiempo.setEnabled(false);
            txtViewTiempo.setText(R.string.minuto);
        }

        txtViewCantidadTiempo.setText(tiempo.toString());
    }

    public void actionChooseImage(View view)
    {startActivityForResult(
            new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI
            ),
            GET_FROM_GALLERY
    );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {

                case GET_FROM_GALLERY:
                    if (resultCode == Activity.RESULT_OK) {
                        Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            LinearLayout imageContainer = findViewById(R.id.idRVImage);
                            View cardView = getLayoutInflater().inflate(R.layout.card_upload_images, null);
                            TextView textView = cardView.findViewById(R.id.idTextImageView);
                            File f = new File(selectedImageUri.getPath());
                            String imageName = f.getName();
                            // update the preview image in the layout
                            textView.setText(imageName);
                            imageContainer.addView(cardView);
                        }
                        break;
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                    }
                    break;
            }
        } catch (Exception e) {
        }
    }

    //Function to convert dp to pixels.
    private int dpTopx(int dp){
        return (int)(dp * getApplicationContext().getResources().getDisplayMetrics().density);
    }
}
