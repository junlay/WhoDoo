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
public class SplashScreenFragment extends Fragment {

    private Button button;
    private Button button2;

    View parentHolder;


    public SplashScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        parentHolder = inflater.inflate(R.layout.fragment_splash_screen,container,false);

        button = (Button)parentHolder.findViewById(R.id.buttonlogin);
        button2 = (Button)parentHolder.findViewById(R.id.buttonsignup);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment, new LoginFragment()).commit();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment, new SignUpFragment()).commit();
            }
        });

        return parentHolder;
    }

}
