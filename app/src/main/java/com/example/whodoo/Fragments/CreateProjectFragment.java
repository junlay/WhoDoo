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
import android.widget.DatePicker;
import android.widget.EditText;
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
public class CreateProjectFragment extends Fragment {
    EditText titleText,descriptionText,addUserText;
    Button createproj, adduser, backButton;
    View parentholder;
    ListView listView;
    List<String> usernames;
    DatePicker datePicker;
    int day, month , year;
    public CreateProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentholder =inflater.inflate(R.layout.fragment_create_project, container, false);
        usernames = new ArrayList<>();
        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        SharedPreferences prefs = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        final String text_for_display = prefs.getString("username",null);
        usernames.add(text_for_display);

        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, usernames){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);

                return view;
            }
        };
        titleText = parentholder.findViewById(R.id.titleText);
        descriptionText = parentholder.findViewById(R.id.descriptionText);
        addUserText = parentholder.findViewById(R.id.addUserText);
        createproj = parentholder.findViewById(R.id.createProjectBtn);
        adduser = parentholder.findViewById(R.id.addMemberBtn);
        listView = parentholder.findViewById(R.id.listView);
        datePicker = parentholder.findViewById(R.id.datePicker2);
        backButton = parentholder.findViewById(R.id.backBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment, new HomeFragment()).commit();
            }
        });

        createproj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleText.getText().toString().isEmpty() || descriptionText.toString().isEmpty() || usernames.isEmpty() ) {
                    Toast.makeText(getContext(), "Insufficent information", Toast.LENGTH_SHORT).show();
                }else {
                    year = datePicker.getYear();
                    month = datePicker.getMonth();
                    day = datePicker.getDayOfMonth();

                    String date = year +"-"+ month+"-"+day;
                    DatabaseSQLite.getInstance(getContext()).addProject(getContext(),titleText.getText().toString(),descriptionText.getText().toString(),date);
                    Cursor data = DatabaseSQLite.getInstance(getContext()).getProjectID(titleText.getText().toString(),descriptionText.getText().toString(),date);
                    int id = 0;
                    while(data.moveToNext()) {
                        id = data.getInt(0);
                    }
                    for (int i=0; i<usernames.size();i++) {
                        DatabaseSQLite.getInstance(getContext()).addProjectUsers(getContext(),id,usernames.get(i));
                    }
                }
            }
        });

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(DatabaseSQLite.getInstance(getContext()).getUsername(getContext(),addUserText.getText().toString())){

                    if(usernames.contains(addUserText.getText().toString())){
                        Toast.makeText(getContext(), "User is already selected", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        usernames.add(addUserText.getText().toString());
                        listView.setAdapter(arrayAdapter);
                        Toast.makeText(getContext(), "User added", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        return parentholder;

    }

}
