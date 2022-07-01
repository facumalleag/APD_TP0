package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecetaFiltroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecetaFiltroFragment extends Fragment {

    LinearLayout parent;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public RecetaFiltroFragment() {
        // Required empty public constructor


/*
        String[] btn_name = {"Button1", "Button2", "Button3", "Button4", "Button5"};
        parent = parent.findViewById(R.id.ll_parent);
        // vv = findViewById(R.id.view_pager);

        for (int i = 0; i < 5; i++) {
            b1 = new Button(RecetaFiltroFragment.this);
            b1.setId(i + 1);
            b1.setText(btn_name[0]);
            b1.setTag(i);
            parent.addView(b1);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str=view.getTag().toString();
                    if (str.equals("0")){
                        Toast.makeText(getApplicationContext(), "Click Button 0",Toast.LENGTH_SHORT).show();

                    }else{
                        if (str.equals("1")){
                            Toast.makeText(getApplicationContext(), "Click Button 1",Toast.LENGTH_SHORT).show();

                        }else{
                            if (str.equals("2")){
                                Toast.makeText(getApplicationContext(), "Click Button 2",Toast.LENGTH_SHORT).show();

                            }else{
                                if (str.equals("3")){
                                    Toast.makeText(getApplicationContext(), "Click Button 3",Toast.LENGTH_SHORT).show();

                                }else{
                                    if (str.equals("4")){
                                        Toast.makeText(getApplicationContext(), "Click Button 4",Toast.LENGTH_SHORT).show();

                                    }else {
                                        if (str.equals("5")){
                                            Toast.makeText(getApplicationContext(), "Click Button 5",Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });


        }//for*/

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecetaFiltroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecetaFiltroFragment newInstance(String param1, String param2) {
        RecetaFiltroFragment fragment = new RecetaFiltroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        final View view = getLayoutInflater().inflate(R.layout.material_io_chip, null);
        LinearLayout layout= parent.findViewById(R.id.ll_parent);
        Chip nameView = view.findViewById(R.id.chip);
        nameView.setText("nameRecipe");
        layout.addView(view);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_receta_filtro, container, false);
    }


}