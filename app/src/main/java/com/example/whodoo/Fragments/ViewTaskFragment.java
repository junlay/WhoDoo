package com.example.whodoo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.whodoo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewTaskFragment extends Fragment {


    Button button2;


    public ViewTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fra
        return inflater.inflate(R.layout.fragment_view_task, container, false);
        
    }

}
