package com.example.whodoo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whodoo.DB.DatabaseSQLite;
import com.example.whodoo.DB.Task;
import com.example.whodoo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTaskFragment extends Fragment {
    Button backbutton,createButton,distrubuteButton, deleteButtom;
    View parentholder;
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
                }else {
                    Task task = new Task(titleText.getText().toString(),desciptionText.getText().toString(),timeText.getText().toString());
                    DatabaseSQLite.getInstance(getContext()).addTask(getContext(),task);
                }

            }
        });
        return parentholder;
    }

}
