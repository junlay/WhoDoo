package com.example.whodoo.Fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.whodoo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private Button createAccountbtn;
    Activity referenceActivity;
    View parentHolder;
    FragmentTransaction fragmentTransaction;
    private EditText field1,field2;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Context context = this.getContext();
        fragmentTransaction = getFragmentManager().beginTransaction();
        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_sign_up, container,
                false);


        field1 = (EditText) parentHolder.findViewById(R.id.field1);
        field2 = (EditText) parentHolder.findViewById(R.id.field2);

        createAccountbtn = (Button) parentHolder.findViewById(R.id.create_accountBtn);

        createAccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return parentHolder;
    }

}
