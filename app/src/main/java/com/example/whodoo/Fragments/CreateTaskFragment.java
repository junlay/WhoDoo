package com.example.whodoo.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whodoo.DB.DatabaseSQLite;
import com.example.whodoo.DB.Task;
import com.example.whodoo.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTaskFragment extends Fragment {
    Button backbutton,createButton,distrubuteButton, deleteButtom;
    View parentholder;
    ListView listView;
    EditText titleText, desciptionText, timeText;

    public CreateTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentholder=inflater.inflate(R.layout.fragment_create_task, container, false);
        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        backbutton = parentholder.findViewById(R.id.backButton);
        createButton = parentholder.findViewById(R.id.addTask);
        titleText = parentholder.findViewById(R.id.taskTitleText);
        desciptionText = parentholder.findViewById(R.id.taskDescription);
        timeText = parentholder.findViewById(R.id.taskTime);
        distrubuteButton = parentholder.findViewById(R.id.distributeTask);
        deleteButtom = parentholder.findViewById(R.id.deleteButtom);

        listView = parentholder.findViewById(R.id.listViewTask);
        SharedPreferences prefs = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        final String text_for_display = prefs.getString("username",null);
        final ArrayList<String> taskTitles = new ArrayList<>();
        final ArrayList<String> usernames = new ArrayList<>();

        Bundle bundle = getArguments();
        String title = bundle.get("project_Title").toString();
        Cursor data = DatabaseSQLite.getInstance(getContext()).getProjectID(title,text_for_display);
        int id = 0;
        while(data.moveToNext()) {
            id = data.getInt(0);
        }

        final Cursor taskTitle = DatabaseSQLite.getInstance(getContext()).getTaskTitle(id);
        while (taskTitle.moveToNext()) {
            taskTitles.add(taskTitle.getString(0));
        }

        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
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

        listView.setAdapter(arrayAdapter);



        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment,new HomeFragment()).commit();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleText.getText().toString().isEmpty()|| desciptionText.getText().toString().isEmpty()|| timeText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {

                    Bundle bundle = getArguments();
                    String title = bundle.get("project_Title").toString();
                    Cursor data = DatabaseSQLite.getInstance(getContext()).getProjectID(title,text_for_display);
                    int id = 0;
                    while(data.moveToNext()) {
                        id = data.getInt(0);
                    }
                    Task task = new Task(titleText.getText().toString(), desciptionText.getText().toString(), timeText.getText().toString());
                    DatabaseSQLite.getInstance(getContext()).addTask(getContext(), task,id);

                    taskTitles.add(task.getTitle());
                    arrayAdapter.notifyDataSetChanged();

                }

            }
        });

        distrubuteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                String title = bundle.get("project_Title").toString();
                Cursor data = DatabaseSQLite.getInstance(getContext()).getProjectID(title,text_for_display);
                int id = 0;
                while(data.moveToNext()) {
                    id = data.getInt(0);
                }
                Cursor users = DatabaseSQLite.getInstance(getContext()).getUser(id);
                while (users.moveToNext()) {
                    usernames.add(users.getString(0));
                }

                for (int i = 0; i<taskTitles.size();i++ ) {
                    Random random = new Random();
                    int number = random.nextInt(usernames.size());
                    int taskID = DatabaseSQLite.getInstance(getContext()).getTaskID(taskTitles.get(i),id);
                    DatabaseSQLite.getInstance(getContext()).addTaskUsers(getContext(),taskID,usernames.get(number));

                }
            }
        });

        return parentholder;
    }

}
