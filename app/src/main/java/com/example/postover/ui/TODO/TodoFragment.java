package com.example.postover.ui.TODO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.Model.ToDoNote;
import com.example.postover.R;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private  List<ToDoNote> todoList;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_todo, container, false);
        todoList = new ArrayList<>();
        recyclerView = root.findViewById(R.id.todoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        todoAdapter = new TodoAdapter(getActivity());
        recyclerView.setAdapter(todoAdapter);

        ToDoNote note = new ToDoNote("Todo note");
        todoList.add(note);
        todoList.add(note);
        todoList.add(note);

        todoAdapter.setTodoList(todoList);


        return root;
    }
}