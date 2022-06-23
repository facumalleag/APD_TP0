package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.model.Ingrediente;

import java.util.ArrayList;
import java.util.List;

public class CreateSecondRecipeActivity extends AppCompatActivity implements DialogoAgregarIngrediente.NoticeDialogListener{

    private EditText editTextTitleRecipe;
    private TextView txtViewCantidadPorcion, txtViewCantidadTiempo;
    private ImageButton btnAgregarPorcion, btnAgregarTiempo;
    private ImageButton btnEliminarPorcion, btnEliminarTiempo;
    private TextView txtViewPorcion, txtViewTiempo;
    private List<Ingrediente> ingredientes = new ArrayList<>();

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


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();


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
        Integer cantidadIngrediente= Integer.valueOf(txtCantIngrediente.getText().toString().trim());
        String nombreIngrediente= txtNombreIngrediente.getText().toString().trim();
        String medida= txtMedida.getSelectedItem().toString();
        Ingrediente ingrediente = new Ingrediente(nombreIngrediente, cantidadIngrediente, medida);
        ingredientes.add(ingrediente);

        LinearLayout btnsContainer = new LinearLayout(getApplicationContext());
        for(Ingrediente ingrediente1: ingredientes){
            final LinearLayout buttonContainer = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_ingrediente, null);
            ImageButton btnEliminarIngrediente = (ImageButton) buttonContainer.findViewById(R.id.btnEliminarIngrediente);
            TextView txtViewIngrediente = (TextView) buttonContainer.findViewById(R.id.txtViewIngrediente);
            txtViewIngrediente.setText(ingrediente1.getNombre());
            if(btnEliminarIngrediente.getParent() != null) {
                ((ViewGroup)btnEliminarIngrediente.getParent()).removeView(btnEliminarIngrediente);
            }
            if(txtViewIngrediente.getParent() != null) {
                ((ViewGroup)txtViewIngrediente.getParent()).removeView(txtViewIngrediente);
            }
            btnsContainer.addView(txtViewIngrediente);
            btnsContainer.addView(btnEliminarIngrediente);
        }
        //Crea contenedor para agregar contenedor de botones.
        FrameLayout.LayoutParams paramsContainer = new FrameLayout.LayoutParams(400, 1500, Gravity.CENTER);
        //Agrega contenedor con botones.
        addContentView(btnsContainer, paramsContainer);

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
}
