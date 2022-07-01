package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.RecetaFiltroFragment;
import com.example.myapplication.databinding.FragmentSearchBinding;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentSearchBinding binding;
    private View root;
    private LinearLayout linearLayout;
    private Integer index;

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

/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View view1 = getLayoutInflater().inflate(R.layout.material_io_chip, null);
        LinearLayout layout=  (LinearLayout) getView().findViewById(R.id.ll_parent);
        Chip nameView = view1.findViewById(R.id.chip);
        nameView.setText("nameRecipe");
        layout.addView(view1);
    }
*/
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        root = inflater.inflate(R.layout.material_io_chip, container, false);
        linearLayout = root.findViewById(R.id.ll_parent);

        final TextView textView = binding.sectionLabel;

        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}