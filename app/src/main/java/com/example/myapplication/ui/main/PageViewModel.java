package com.example.myapplication.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;

public class PageViewModel extends ViewModel {
    private View root;
    private LinearLayout linearLayout;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void createChips(){
//        root = inflater.inflate(R.layout.material_io_chip, container, false);
 //       linearLayout = root.findViewById(R.id.ll_parent);
    }
}