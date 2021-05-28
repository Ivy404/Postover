package com.example.postover.ui.TODO;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.MainActivity;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<ToDoNote> todoList;
    FragmentActivity mainActivity;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


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
    public Context getContext() {
        return mainActivity;
    }

    public TodoAdapter(FragmentActivity mainActivity) {
        this.mainActivity = mainActivity;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void setTodoList(List<ToDoNote> dataSet) {
        todoList = dataSet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_todo_task, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.task.setText(todoList.get(position).getTitle());
        holder.task.setChecked(todoList.get(position).isCompleted());
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    todoList.get(position).setCompleted(true);
                    mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("todoList").setValue(todoList);

                } else {
                    todoList.get(position).setCompleted(false);
                    mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("todoList").setValue(todoList);
                }
            }
        });
    }
    public void deleteItem(int position) {
        ToDoNote item = todoList.get(position);
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("todoList").
                child(Integer.toString(position)).removeValue();
        int i = 0;
        todoList.removeAll(Collections.singletonList(null));
        for(ToDoNote t : todoList){
            if(t.getId().equals(item.getId())){
                todoList.remove(i);
                break;
            }i++;
        }
        todoList.removeAll(Collections.singletonList(null));
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("todoList").setValue(todoList);


        notifyDataSetChanged();
    }
    public void editItem(int position) {
        ToDoNote item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", item);
        bundle.putString("id",item.getId());
        AddTodo fragment = new AddTodo(getContext());
        fragment.setArguments(bundle);
        fragment.show(mainActivity.getSupportFragmentManager(),"Add todo");
    }

    @Override
    public int getItemCount() {
        if(todoList == null){return 0;}
       return  todoList.size();
    }
}
