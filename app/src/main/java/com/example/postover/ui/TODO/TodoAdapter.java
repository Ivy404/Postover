package com.example.postover.ui.TODO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.MainActivity;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<ToDoNote> todoList;
    FragmentActivity mainActivity;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox task;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            task = view.findViewById(R.id.todoCheckBox);
        }

        public TextView getTextView() {
            return task;
        }
    }

    public TodoAdapter(FragmentActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public void setTodoList(List<ToDoNote> dataSet) {
        todoList = dataSet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_todo_task, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.task.setText(todoList.get(position).getTitle());
        holder.task.setChecked(todoList.get(position).getCompleted());
    }

    @Override
    public int getItemCount() {
       return  todoList.size();
    }
}
