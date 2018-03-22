package com.example.whodoo.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Junnan on 2018-03-19.
 */

public class DatabaseSQLite extends SQLiteOpenHelper {
    private static DatabaseSQLite sInstance;

    public static synchronized DatabaseSQLite getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (sInstance == null) {
            sInstance = new DatabaseSQLite(context.getApplicationContext());
        }
        return sInstance;
    }


    public static final String DATABASE_NAME = "whodo.db";

    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table projects(project_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, deadline TEXT)");

        db.execSQL("create table users (username TEXT PRIMARY KEY, password TEXT) ");

        db.execSQL("create table tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, deadline TEXT, " +
                "project_id INTEGER, FOREIGN KEY (project_id) REFERENCES projects (project_id))");

        db.execSQL("create table projects_has_users (projects_project_id INTEGER, users_username TEXT, FOREIGN KEY (projects_project_id) REFERENCES projects (project_id), " +
                "FOREIGN KEY (users_username) REFERENCES users (username), PRIMARY KEY (projects_project_id, users_username))");

        db.execSQL("create table users_has_tasks (tasks_task_id INTEGER , users_username TEXT, " +
                "FOREIGN KEY (tasks_task_id) REFERENCES tasks(task_id), FOREIGN KEY (users_username) REFERENCES users (username), " +
                " PRIMARY KEY (tasks_task_id , users_username))");

    }

    public void addTask(Context context, Task task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("title",task.getTitle());
        cv.put("description",task.getDescription());
        cv.put("deadline",task.getDeadline());

        long res = db.insert("tasks",null,cv);

        if (res == -1){
            Toast.makeText(context,"Upload failed!",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(context, "Upload succeded!", Toast.LENGTH_LONG).show();

        db.close();

    }

    public boolean checkUser(Context context, String email, String password) {

        // array of columns to fetch
        String[] columns = {
                "username"
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = "username" + " = ?" + " AND " + "password" + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query("users", //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            Toast.makeText(context,"Success!",Toast.LENGTH_LONG).show();
            return true;
        }

        Toast.makeText(context,"Failed!",Toast.LENGTH_LONG).show();
        return false;
    }

    public boolean getUsername(Context context, String username) {

        // array of columns to fetch
        String[] columns = {
                "username"
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = "username" + " = ?";

        // selection arguments
        String[] selectionArgs = {username};

        // query user table with conditions

        Cursor cursor = db.query("users", //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        Toast.makeText(context, "User doesn't exist", Toast.LENGTH_SHORT).show();

        return false;
    }

    public void addProject(Context context, String title, String description, String deadline){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("title",title);
        cv.put("description",description);
        cv.put("deadline",deadline);


        long res = db.insert("projects",null,cv);

        if (res == -1){
            Toast.makeText(context,"Upload failed!",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(context, "Upload succeded!", Toast.LENGTH_LONG).show();

        db.close();

    }

    public void addUsername(Context context, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("username",username);
        cv.put("password",password);

        long res = db.insert("users",null,cv);

        if (res == -1){
            Toast.makeText(context,"Upload failed!",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(context, "Upload succeded!", Toast.LENGTH_LONG).show();

        db.close();

    }

    public String getTaskTitle(){
        SQLiteDatabase db = getReadableDatabase();
        String title = null;

        String query = "Select title from tasks where id = 10000";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                title = cursor.getString(0);


            } while (cursor.moveToNext());
        }

        return title;
    }

    public Cursor getProject(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select title from projects inner join projects_has_users on projects.project_id = projects_has_users.projects_project_id where users_username = '"+username+"'";
        Cursor data = db.rawQuery(query,null);

        return data;

    }

    public Cursor getProjectID(String title,String description,String deadline) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select project_id from projects where title = '" + title+"' and description = '" + description +"' and deadline = '" +deadline+"'" ;
        Cursor data = db.rawQuery(query,null);

        return data;

    }

    public void addProjectUsers(Context context, int id, String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("projects_project_id",id);
        cv.put("users_username",username);

        long res = db.insert("projects_has_users",null,cv);

        if (res == -1){
            Toast.makeText(context,"Upload failed!",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(context, "Upload succeded!", Toast.LENGTH_LONG).show();

        db.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + "users");
        db.execSQL("DROP TABLE IF EXISTS " + "projects");
        db.execSQL("DROP TABLE IF EXISTS " + "tasks");


        // Create tables again
        onCreate(db);

    }
}
