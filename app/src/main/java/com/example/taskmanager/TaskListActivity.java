package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;


import com.example.taskmanager.databinding.ActivityTasklistBinding;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    private ActivityTasklistBinding binding;
    TaskAdapter taskAdapter;
    ArrayList<TaskItems> taskArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTasklistBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        taskArrayList = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(taskArrayList);
        binding.recyclerView.setAdapter(taskAdapter);
        getData();
    }

    public void getData(){


        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Tasks",MODE_PRIVATE,null);

            Cursor cursor = database.rawQuery("SELECT * FROM tasks", null);
            int tasknameIx = cursor.getColumnIndex("taskname");
            int dateIx = cursor.getColumnIndex("date");
            int idIx = cursor.getColumnIndex("id");

            while (cursor.moveToNext()) {
                String taskname = cursor.getString(tasknameIx);
                String date = cursor.getString(dateIx);
                int id = cursor.getInt(idIx);
                TaskItems taskItems = new TaskItems(taskname,id,date);
                taskArrayList.add(taskItems);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}