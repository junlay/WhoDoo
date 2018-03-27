package com.example.whodoo.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.whodoo.DB.DatabaseSQLite;
import com.example.whodoo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewTaskFragment extends Fragment {


    Button backButton;
    View parentHolder;
    ListView taskTitleListView;
    ListView taskDescriptionListView;
    ListView taskTimeListView;


    public ViewTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        SharedPreferences prefs = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        ArrayList<String> taskTitles = new ArrayList<>();
        ArrayList<String> taskDescriptions = new ArrayList<>();
        ArrayList<String> taskTime = new ArrayList<>();

        final String text_for_display = prefs.getString("username",null);
        parentHolder = inflater.inflate(R.layout.fragment_view_task, container, false);
        backButton = parentHolder.findViewById(R.id.backButton3);
        taskTitleListView = parentHolder.findViewById(R.id.taskTitleView);
        taskDescriptionListView = parentHolder.findViewById(R.id.taskDescriptionView);
        taskTimeListView = parentHolder.findViewById(R.id.taskTimeView);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragment,new HomeFragment()).commit();
            }
        });

        Cursor taskData = DatabaseSQLite.getInstance(getContext()).getTask(text_for_display);

        while (taskData.moveToNext()) {
            taskTitles.add(taskData.getString(0));
            taskDescriptions.add(taskData.getString(2));
            taskTime.add(taskData.getString(1));
        }

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, taskTitles){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);

                return view;
            }
        };


        ArrayAdapter<String> arrayAdapter2=new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, taskDescriptions){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);

                return view;
            }
        };

        ArrayAdapter<String> arrayAdapter3=new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, taskTime){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);

                return view;
            }
        };

        taskTitleListView.setAdapter(arrayAdapter);
        taskTimeListView.setAdapter(arrayAdapter3);
        taskDescriptionListView.setAdapter(arrayAdapter2);



        return parentHolder;
    }

}
