package com.example.taskmanager;

import java.io.Serializable;

public class TaskItems implements Serializable {

        String taskname;
        int id;
        String date;
        public TaskItems(String taskname, int id, String date) {
            this.taskname = taskname;
            this.id = id;
            this.date = date;
        }
}
