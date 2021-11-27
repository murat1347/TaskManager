package com.example.taskmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

import com.example.taskmanager.databinding.RecyclerRowBinding;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TasksHolder> {


    ArrayList<TaskItems> TaskItemList;

    public TaskAdapter(ArrayList<TaskItems> TaskItemList) {
        this.TaskItemList = TaskItemList;

    }

    @Override
    public TasksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TasksHolder(recyclerRowBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull TasksHolder holder, int position) {

        Button button_update = holder.binding.btnUpdate;
        button_update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.itemView.getContext(), TaskDetails.class);
                        intent.putExtra("id", TaskItemList.get(position).id);
                        intent.putExtra("info", "new");
                        holder.itemView.getContext().startActivity(intent);
                    }
                }
        );
        Button button_delete = holder.binding.btnDelete;
        button_delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = TaskItemList.get(position).id;
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Delete");
                        builder.setMessage("Do you want to delete record?");
                        builder.setNegativeButton("No", null);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SQLiteDatabase database;
                                database = v.getContext().openOrCreateDatabase("Tasks", MODE_PRIVATE, null);
                                String sqlString = "DELETE FROM tasks WHERE id = ?";
                                SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                                sqLiteStatement.bindString(1, String.valueOf(TaskItemList.get(position).id));
                                sqLiteStatement.execute();
                                Intent intent = new Intent(v.getContext(), TaskListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                v.getContext().startActivity(intent);
                            }
                        });
                        builder.show();

                    }
                }
        );
        holder.binding.recylerViewTextView.setText(TaskItemList.get(position).taskname);
        holder.binding.date.setText(TaskItemList.get(position).date);


    }

    @Override
    public int getItemCount() {
        return TaskItemList.size();
    }

    public class TasksHolder extends RecyclerView.ViewHolder {

        private RecyclerRowBinding binding;

        public TasksHolder(@NonNull RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
