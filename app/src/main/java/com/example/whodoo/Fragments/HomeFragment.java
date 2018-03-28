package com.example.whodoo.Fragments;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
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
import com.example.whodoo.MainActivity;
import com.example.whodoo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Button logoutButton, createProject, rightButton, messengerButton, viewTask;
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
        messengerButton = (Button) parentHolder.findViewById(R.id.messenger);
        projectListview = (ListView) parentHolder.findViewById(R.id.projectListView);
        viewTask = (Button) parentHolder.findViewById(R.id.viewTask);



        viewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment,new ViewTaskFragment()).commit();
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

        messengerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragmentTransaction.replace(R.id.fragment,new CreateProjectFragment()).commit();
         }
        });



        createProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fragmentTransaction.replace(R.id.fragment,new CreateProjectFragment()).commit();
            }
        });


        Cursor data = DatabaseSQLite.getInstance(getContext()).getProject(text_for_display);
        final ArrayList<String> projects = new ArrayList<>();
        while (data.moveToNext()) {
            projects.add(data.getString(0));
        }

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, projects){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);

                return view;
            }
        };

        projectListview.setAdapter(arrayAdapter);


        projectListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putString("project_Title",projects.get(position));
                CreateTaskFragment createTaskFragment = new CreateTaskFragment();
                createTaskFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment,createTaskFragment);
                fragmentTransaction.commit();
            }
        });


        return parentHolder;
    }


}


