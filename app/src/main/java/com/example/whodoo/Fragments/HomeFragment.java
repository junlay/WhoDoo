package com.example.whodoo.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.whodoo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Button logoutButton, leftButton, rightButton;
    View parentHolder;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        parentHolder = inflater.inflate(R.layout.fragment_home, container, false);

        logoutButton = (Button) parentHolder.findViewById(R.id.logoutButton);
        leftButton = (Button) parentHolder.findViewById(R.id.homeLeftButton);
        rightButton = (Button) parentHolder.findViewById(R.id.homeRightButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();

                fragmentTransaction.replace(R.id.fragment,new LoginFragment()).commit();
                Toast.makeText(getContext(),"Logged out",Toast.LENGTH_LONG).show();
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment,new CreateProjectFragment()).commit();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment,new CreateTaskFragment()).commit();
            }
        });



        return parentHolder;
    }

}
