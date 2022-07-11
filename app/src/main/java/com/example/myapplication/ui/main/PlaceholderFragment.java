package com.example.myapplication.ui.main;

import static com.example.myapplication.Constants.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.ShowRecipeActivity;
import com.example.myapplication.controller.RecipesController;
import com.example.myapplication.databinding.FragmentSearchBinding;
import com.example.myapplication.services.CategoryService;
import com.example.myapplication.services.IngredientService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentSearchBinding binding;
    private View root;
    private LinearLayout linearLayoutCategory;
    private LinearLayout linearLayoutIngredient;
    private LinearLayout linearLayoutLackOfIngredient;
    private Integer index;
    LayoutInflater inflater;
    ViewGroup container;
    RecipesController RecpCont;

    public static PlaceholderFragment newInstance(int index) {

        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        this.index = index;
        pageViewModel.setIndex(index);


    }

    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container= container;
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view =  getLayoutInflater().inflate(R.layout.fragment_receta_filtro, null);
        root = view;
        TextView ayuda =  view.findViewById(R.id.txtViewAyuda);

        RecpCont = RecipesController.getInstancia();
        final TextView textView = binding.sectionLabel;

        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        linearLayoutCategory  =  view.findViewById(R.id.ll_parent_category);
        linearLayoutIngredient  =  view.findViewById(R.id.ll_parent_ingredient);
        linearLayoutLackOfIngredient = view.findViewById(R.id.ll_parent_lack_of_ingredient);
        getCategories();
        getIngredient();

        if (this.index == 1){
            //getCategories();
            linearLayoutCategory.setVisibility(View.VISIBLE);
        }else{
            linearLayoutCategory.setVisibility(View.INVISIBLE);
        }

        if (this.index == 4){
            view.findViewById(R.id.SearchViewUserForRecipes).setVisibility(View.VISIBLE);
            ayuda.setVisibility(View.VISIBLE);
        }else{
            view.findViewById(R.id.SearchViewUserForRecipes).setVisibility(View.INVISIBLE);
            ayuda.setVisibility(View.INVISIBLE);
        }
        if (this.index == 2){
            linearLayoutIngredient.setVisibility(View.VISIBLE);
        }else{
            linearLayoutIngredient.setVisibility(View.INVISIBLE);
        }
        if (this.index == 3){
            linearLayoutLackOfIngredient.setVisibility(View.VISIBLE);
        }else{
            linearLayoutLackOfIngredient.setVisibility(View.INVISIBLE);
        }
        SearchView sv = view.findViewById(R.id.SearchViewUserForRecipes);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RecpCont.setUser_name_for_search(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
                RecpCont.setUser_name_for_search(newText);
                //}
                return true;
            }

            public void callSearch(String query) {
                System.out.println(query);
            }

        });
        /*


        if (this.index == 1){
            view.findViewById(R.id.SearchViewRecipe).setVisibility(View.VISIBLE);
        }else{
            view.findViewById(R.id.SearchViewRecipe).setVisibility(View.INVISIBLE);

        }
        */

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getCategories(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CategoryService cs = retrofit.create(CategoryService.class);
        //Call<JsonElement> call = fs.listFavoriteRecipesByUserId( Integer.toString(UserController.getInstancia().getUserId()));
        Call<JsonElement> call = cs.listCategories();
        //OJO el id del usuario esta hardcodeado
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //lblEstado.setText(response.body() );
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    System.out.println("");
                    createCategoryChips(response.body().getAsJsonObject().get("listedCategories").getAsJsonArray());


                } else {
                    if (response.code() == 400) {
                        Toast toast = Toast.makeText(getContext().getApplicationContext(), "Ocurrio un error, intente mas tarde", Toast.LENGTH_SHORT);
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
                 //lblEstado.setText(response.body() );
                 if (response.isSuccessful()) {
                     System.out.println(response.body());
                     System.out.println("");
                     createIngredientChips(response.body().getAsJsonObject().get("listedIngredients").getAsJsonArray());
                     createLackIngredientChips(response.body().getAsJsonObject().get("listedIngredients").getAsJsonArray());
                 } else {
                     if (response.code() == 400) {
                         Toast toast = Toast.makeText(getContext().getApplicationContext(), "Ocurrio un error, intente mas tarde", Toast.LENGTH_SHORT);
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






    public void search(){
        ChipGroup chipGroup = linearLayoutCategory.findViewById(R.id.chipGroup);
        List<Integer> ids =  chipGroup.getCheckedChipIds();
        System.out.println("Ingredientes");
        for (Integer id:ids){
            Chip chip = chipGroup.findViewById(id);
            System.out.println(chip.getId());
        }
        ChipGroup chipGroup2 = linearLayoutCategory.findViewById(R.id.chipGroup);
        List<Integer> ids2 =  chipGroup.getCheckedChipIds();
        System.out.println("falta de ingredientes");
        for (Integer id:ids2){
            Chip chip = chipGroup2.findViewById(id);
            System.out.println(chip.getId());
        }
        ChipGroup chipGroup3 = linearLayoutCategory.findViewById(R.id.chipGroup);
        List<Integer> ids3 =  chipGroup.getCheckedChipIds();
        System.out.println("categorias");
        for (Integer id:ids3){
            Chip chip = chipGroup3.findViewById(id);
            System.out.println(chip.getId());
        }
    }
    public void createCategoryChips (JsonArray lista){
        View v =  inflater.inflate(R.layout.material_io_chip_group, container,false);
        ChipGroup chipGroup =  v.findViewById(R.id.chipGroup);
        chipGroup.setSingleSelection(false);

        for(JsonElement category : lista){
            Integer id = category.getAsJsonObject().get("id").getAsInt();
            String name = category.getAsJsonObject().get("description").getAsString();
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout, chipGroup, false);
            chip.setText(name);
            chip.setId(id);
            if(RecpCont.isCategoryChecked(id)){chip.setChecked(true);}
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(RecpCont.isCategoryChecked(id)){
                        RecipesController.getInstancia().removeCategory_for_search(id);
                    }else{
                        RecipesController.getInstancia().addcategory_for_search(id);
                    }
                }
            });
            chipGroup.addView(chip);
        }
        if(chipGroup.getParent() != null) {
            ((ViewGroup)chipGroup.getParent()).removeView(chipGroup); // <- fix
        }
        linearLayoutCategory.addView(chipGroup);
    }
    public void createIngredientChips (JsonArray lista){
        View v =  inflater.inflate(R.layout.material_io_chip_group, container,false);
        ChipGroup chipGroup =  v.findViewById(R.id.chipGroup);
        chipGroup.setSingleSelection(false);
        for(JsonElement category : lista){
            int id = category.getAsJsonObject().get("id").getAsInt();
            String name = category.getAsJsonObject().get("description").getAsString();
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout, chipGroup, false);
            chip.setText(name);
            chip.setId(id);
            if(RecpCont.isIngredientChecked(id)){chip.setChecked(true);}
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(RecpCont.isIngredientChecked(id)){
                        RecipesController.getInstancia().removeIngredients_for_search(id);
                    }else{
                        RecipesController.getInstancia().addingredients_for_search(id);
                    }
                }
            });
            chipGroup.addView(chip);
        }
        if(chipGroup.getParent() != null) {
            ((ViewGroup)chipGroup.getParent()).removeView(chipGroup); // <- fix
        }
        linearLayoutIngredient.addView(chipGroup);
    }
    public void createLackIngredientChips(JsonArray lista){
        View v =  inflater.inflate(R.layout.material_io_chip_group, container,false);
        ChipGroup chipGroup =  v.findViewById(R.id.chipGroup);
        chipGroup.setSingleSelection(false);
        for(JsonElement category : lista){
            int id = category.getAsJsonObject().get("id").getAsInt();
            String name = category.getAsJsonObject().get("description").getAsString();
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout, chipGroup, false);
            chip.setText(name);
            chip.setId(id);
            if(RecpCont.isLackOfIngredientChecked(id)){chip.setChecked(true);}
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(RecpCont.isLackOfIngredientChecked(id)){
                        RecipesController.getInstancia().removeLack_of_ingredients_for_search(id);
                    }else{
                        RecipesController.getInstancia().addlack_of_ingredients_for_search(id);
                    }
                }
            });
            chipGroup.addView(chip);
        }
        if(chipGroup.getParent() != null) {
            ((ViewGroup)chipGroup.getParent()).removeView(chipGroup); // <- fix
        }
        linearLayoutLackOfIngredient.addView(chipGroup);
    }

}

