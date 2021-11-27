package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;


import com.example.taskmanager.databinding.ActivityCreateBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity {

    private ActivityCreateBinding binding;
    SQLiteDatabase database;
    final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                binding.date2.setText(sdf.format(myCalendar.getTime()));
            }

        };

        binding.date2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


    }

    public void save(View view) {
        String taskname = binding.taskname.getText().toString();
        String description = binding.description.getText().toString();
        String date = binding.date2.getText().toString();


        try {
            database = this.openOrCreateDatabase("Tasks", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS tasks(id INTEGER PRIMARY KEY,taskname VARCHAR,description VARCHAR,date VARCHAR)");
            String sqlString = "INSERT INTO tasks(taskname,description,date) VALUES (?,?,?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1, taskname);
            sqLiteStatement.bindString(2, description);
            sqLiteStatement.bindString(3, date);
            sqLiteStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(CreateActivity.this, MainActivity.class);
        startActivity(intent);
    }


}