package com.example.myapplication;

import static com.example.myapplication.Constants.ID_RECIPE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.myapplication.controller.NetworkController;
import com.example.myapplication.controller.RecipesController;
import com.example.myapplication.enums.StatusEnum;
import com.example.myapplication.model.Ingrediente;
import com.example.myapplication.model.Paso;
import com.example.myapplication.model.Receta;
import com.example.myapplication.services.CategoryService;
import com.example.myapplication.services.RecipeService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateSecondRecipeActivity extends AppCompatActivity implements DialogoAgregarIngrediente.NoticeDialogListener,DialogoRedDisponible.NoticeDialogListener {

    private EditText editTextTitleRecipe, editTextDescripcion;
    private TextView txtViewCantidadPorcion, txtViewCantidadTiempo;
    private ImageButton btnAgregarPorcion, btnAgregarTiempo, btnChooseImage;
    private ImageButton btnEliminarPorcion, btnEliminarTiempo;
    private TextView txtViewPorcion, txtViewTiempo;
    private List<Ingrediente> ingredientes = new ArrayList<>();
    private NetworkController controlador_red=NetworkController.getInstancia();
    public static final int GET_FROM_GALLERY = 3;
    ImageView previewImage;
    private String idRecipe;
    private int idIngrediente = 0;
    private RecipesController recetas=RecipesController.getInstancia();
    //private DialogoRedDisponible dialogo=new DialogoRedDisponible();

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
            idRecipe = parametros.getString(ID_RECIPE);
        }

        btnAgregarPorcion = findViewById(R.id.btnAgregarPorcion);
        btnEliminarPorcion = findViewById(R.id.btnEliminarPorcion);
        btnAgregarTiempo = findViewById(R.id.btnAgregarTiempo);
        btnEliminarTiempo = findViewById(R.id.btnEliminarTiempo);
        txtViewCantidadPorcion = findViewById(R.id.txtViewCantidadPorcion);
        txtViewPorcion = findViewById(R.id.txtViewPorcion);
        txtViewCantidadTiempo = findViewById(R.id.txtViewCantidadTiempo);
        txtViewTiempo = findViewById(R.id.txtViewTiempo);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        getCategories();
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();


    }

    public void getCategories(){
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(Constants.BASE_URL)
                .baseUrl("https://tpoapd2022.herokuapp.com")
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
        LinearLayout btnsContainer = findViewById(R.id.container_categories);
        String categorieName;

        ChipGroup chipGroup = (ChipGroup) btnsContainer.getChildAt(0);
        List<Integer> ids = chipGroup.getCheckedChipIds();
        int idCategorie;
        for (Integer id:ids){
            Chip chip = chipGroup.findViewById(id);
            categorieName = chip.getText().toString();
            idCategorie = chip.getId();
        }
        Integer porciones = Integer.valueOf(txtViewCantidadPorcion.getText().toString());
        Integer tiempo = Integer.valueOf(txtViewCantidadTiempo.getText().toString());
        String descripcion = editTextDescripcion.getText().toString();


        String title = editTextTitleRecipe.getText().toString();

        LinearLayout container_pasos = findViewById(R.id.container_pasos);
        List<Paso> pasos = new ArrayList<>();
        int cantidadPasos = container_pasos.getChildCount();
        for (int index = 0; index < cantidadPasos; index++) {
            View itemPasoContainer = container_pasos.getChildAt(index);
            EditText txtPaso = itemPasoContainer.findViewById(R.id.txtViewPaso);
            String descripcionPaso = txtPaso.getText().toString();
            if(descripcionPaso.isEmpty()){
                txtPaso.setError("La descripción del paso no puede estar vacia");
                return;
            }
            Paso paso = new Paso(index+1, descripcionPaso);
            pasos.add(paso);
        }

        Receta receta = new Receta(0,1005, title, descripcion, porciones, 1, tiempo, StatusEnum.IN_PROGRESS.getId(),
                1,1, "false", cantidadPasos, "", new Date(), new Date(), ingredientes, pasos);

        //agregar el if
        String tipoconexion=controlador_red.verificarTipoRed(this);
        if(tipoconexion.equals("WIFI")) {
            guardarReceta(receta);
        }else {
            if(tipoconexion.equals("NO_RED")){
                showSnackBarNoNetwork(tipoconexion);
            }
            else{
                recetas.agregarreceta(receta);
                abrirDialogo();
            }
        }

        if(idRecipe != null){
            eliminarReceta(idRecipe);
        }
        continuarVentanaDeFinCreacion();
    }

    public void abrirDialogo() {
        DialogFragment newFragment = new DialogoRedDisponible();
        newFragment.show(getSupportFragmentManager(), "Atención");
    }


    @Override
    public void enDialogoPositivoClick(DialogFragment dialog) {
        readfile(this);
    }

    @Override
    public void enDialogoNegativoClick(DialogFragment dialog) {
        saveFile(this);
    }



    private void saveFile(Context context) {
        recetas.guardarRecetaEnAlmInterno(context);
    }

    private void readfile(Context context) {
        recetas.leerArchivoReceta(context);
    }

    private void showSnackBarNoNetwork(String isConnected) {

        // initialize color and message
        String message;
        int color= Color.RED;;
        message = "Not Connected to Internet";

        // initialize snack bar
        Snackbar snackbar = Snackbar.make(findViewById(R.id.btnLogin), message, Snackbar.LENGTH_LONG);

        // initialize view
        View view = snackbar.getView();

        // Assign variable
        TextView textView = view.findViewById(R.id.snackbar_text);

        // set text color
        textView.setTextColor(color);

        // show snack bar
        snackbar.show();
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
            return;
        }

        if (txtCantIngrediente.getText().toString().trim() == null || txtCantIngrediente.getText().toString().isEmpty()){
            txtNombreIngrediente.setError("La cantidad debe ser mayor a cero");
        } else {
            cantidadIngrediente = Integer.valueOf(txtCantIngrediente.getText().toString().trim());
        }

        Ingrediente ingrediente = new Ingrediente(idIngrediente, nombreIngrediente, cantidadIngrediente, medida);
        ingredientes.add(ingrediente);

        LinearLayout ingredienteContainer = findViewById(R.id.container_ingredientes);
        View itemIngredienteContainer =  LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_ingrediente, null);
        ImageButton btnEliminarIngrediente = (ImageButton) itemIngredienteContainer.findViewById(R.id.btnEliminarIngrediente);
        btnEliminarIngrediente.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                LinearLayout linearLayout =(LinearLayout) v.getParent();
                TextView txtViewId = (TextView) linearLayout.findViewById(R.id.txtViewId);
                int id = Integer.valueOf(txtViewId.getText().toString());
                ingredientes.remove(id);
                ingredienteContainer.removeView((LinearLayout)(v.getParent()));

            }});
        TextView txtViewIngrediente = (TextView) itemIngredienteContainer.findViewById(R.id.txtViewIngrediente);
        txtViewIngrediente.setText(ingrediente.getNombre());
        TextView txtViewCantidad = (TextView) itemIngredienteContainer.findViewById(R.id.txtViewCantidad);
        txtViewCantidad.setText(ingrediente.getCantidad() + " " +  ingrediente.getMedida());
        TextView textViewIdIngrediente = (TextView) itemIngredienteContainer.findViewById(R.id.txtViewId);
        textViewIdIngrediente.setText(String.valueOf(idIngrediente));
        ingredienteContainer.addView(itemIngredienteContainer);
        idIngrediente ++;

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void actionAgregarIngrediente(View view){
        DialogFragment newFragment = new DialogoAgregarIngrediente();
        newFragment.show(getSupportFragmentManager(), "Atención");
    }

    public void actionAgregarPaso(View view){
        LinearLayout pasosContainer = findViewById(R.id.container_pasos);
        View itemPasoContainer =  LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_paso, null);
        ImageButton btnEliminarPaso = (ImageButton) itemPasoContainer.findViewById(R.id.btnEliminarPaso);
        btnEliminarPaso.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                pasosContainer.removeView((LinearLayout)(v.getParent()));
            }});
        pasosContainer.addView(itemPasoContainer);

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

                            ImageButton btnEliminarImagen = (ImageButton) cardView.findViewById(R.id.btnEliminarImagen);
                            btnEliminarImagen.setOnClickListener(new View.OnClickListener(){

                                @Override
                                public void onClick(View v) {
                                    imageContainer.removeView((LinearLayout)(v.getParent().getParent().getParent()));
                                }});
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

    private void guardarReceta(Receta receta){
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(Constants.BASE_URL)
                .baseUrl("https://tpoapd2022.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService fs = retrofit.create(RecipeService.class);
        Call<JsonElement> call = fs.createRecipe(receta);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    System.out.println("");

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

    public void continuarVentanaDeFinCreacion(){
        Intent intent = new Intent(this, RecetaGuardadaLayout.class);
        startActivity(intent);
    }

    private void eliminarReceta(String idRecipe) {
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(Constants.BASE_URL)
                .baseUrl("https://tpoapd2022.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService fs = retrofit.create(RecipeService.class);
        Call<JsonElement> call = fs.deleteRecipe(idRecipe);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    System.out.println("");

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,HomeApplicationActivity.class);
        startActivity(intent);
        finish();
    }

}
