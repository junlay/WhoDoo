package com.example.whodoo.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whodoo.DB.DatabaseSQLite;
import com.example.whodoo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Button logoutButton, createProject, rightButton, addTaskbtn, viewTask, deleteBtn;
    View parentHolder;
    ListView projectListview;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences prefs = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        final String text_for_display = prefs.getString("username",null);

        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        parentHolder = inflater.inflate(R.layout.fragment_home, container, false);


        logoutButton = (Button) parentHolder.findViewById(R.id.logoutButton);
        createProject = (Button) parentHolder.findViewById(R.id.projectCreate);
        //rightButton = (Button) parentHolder.findViewById(R.id.homeRightButton);
        projectListview = (ListView) parentHolder.findViewById(R.id.projectListView);
        addTaskbtn = (Button) parentHolder.findViewById(R.id.addTaskbtn);
        viewTask = (Button) parentHolder.findViewById(R.id.viewTask);
        deleteBtn = (Button) parentHolder.findViewById(R.id.deleteBtn);


        viewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment,new ViewTaskFragment()).commit();
            }
        });


        addTaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment,new CreateTaskFragment()).commit();
            }
        });



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();

                fragmentTransaction.replace(R.id.fragment,new LoginFragment()).commit();
                Toast.makeText(getContext(),"Logged out",Toast.LENGTH_LONG).show();
            }
        });

        //rightButton.setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View view) {
                //fragmentTransaction.replace(R.id.fragment,new CreateProjectFragment()).commit();
        // }
        //});



        createProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fragmentTransaction.replace(R.id.fragment,new CreateProjectFragment()).commit();
            }
        });

        showProjects(text_for_display);



        return parentHolder;
    }

    public void showProjects(String username) {
        Cursor data = DatabaseSQLite.getInstance(getContext()).getProject(username);
        ArrayList<String> projects = new ArrayList<>();
        while (data.moveToNext()) {
            projects.add(data.getString(0));
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1, projects);

        projectListview.setAdapter(arrayAdapter);

    }



}
