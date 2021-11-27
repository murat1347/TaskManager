package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;

import com.example.taskmanager.databinding.ActivityTaskDetailsBinding;


public class TaskDetails extends AppCompatActivity {

    SQLiteDatabase database;
    private ActivityTaskDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        database = this.openOrCreateDatabase("Tasks",MODE_PRIVATE,null);
        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        if (info.equals("new")){


            int id = intent.getIntExtra("id", 1);
            try {

                Cursor cursor = database.rawQuery("SELECT * FROM tasks WHERE id = ?", new String[]{String.valueOf(id)});

                int tasknameIx = cursor.getColumnIndex("taskname");
                int descriptionIx = cursor.getColumnIndex("description");
                int dateIx = cursor.getColumnIndex("date");
                while (cursor.moveToNext()) {

                    binding.taskname.setText(cursor.getString(tasknameIx));
                    binding.description.setText(cursor.getString(descriptionIx));
                    binding.date.setText(cursor.getString(dateIx));

                }

                cursor.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        binding.saveupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = intent.getIntExtra("id", 1);
                String taskname = binding.taskname.getText().toString();
                String description = binding.description.getText().toString();
                String date = binding.date.getText().toString();


                try {
                    String sqlString = "UPDATE tasks SET taskname=?, description=?, date=?  WHERE id=? ";
                    SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                    sqLiteStatement.bindString(1, taskname);
                    sqLiteStatement.bindString(2, description);
                    sqLiteStatement.bindString(3, date);
                    sqLiteStatement.bindString(4, String.valueOf(id));
                    sqLiteStatement.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(v.getContext(), TaskListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(intent);
            }
        });

    }
    }
