package com.example.whodoo.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whodoo.DB.DatabaseSQLite;
import com.example.whodoo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private Button loginButton,backButton;
    Activity referenceActivity;
    View parentHolder;
    private EditText username_field, password_field;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Context context = getContext();

        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_log_in, container,
                false);

        username_field = (EditText)parentHolder.findViewById(R.id.username_field);
        password_field = (EditText)parentHolder.findViewById(R.id.password_field);


        loginButton = (Button) parentHolder.findViewById(R.id.loginButton);
        backButton = (Button) parentHolder.findViewById(R.id.backButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username_field.getText().length() > 0 && password_field.getText().length() > 0) {
                    if (DatabaseSQLite.getInstance(context).checkUser(context, username_field.getText().toString()
                            , password_field.getText().toString()) == true) {
                        SharedPreferences sharedPref = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", username_field.getText().toString());
                        editor.putString("password", password_field.getText().toString());
                        editor.commit();


                        fragmentTransaction.replace(R.id.fragment, new HomeFragment()).commit();

                    }else {

                    }

                }else {
                    Toast.makeText(getContext(), "One or both fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment,new SplashScreenFragment()).commit();
            }
        });


        return parentHolder;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
