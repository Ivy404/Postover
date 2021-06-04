package com.example.postover.ui.TODO;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postover.Model.Client;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;
import com.example.postover.ui.home.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class TodoFragment extends Fragment {
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private List<ToDoNote> todoList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_todo, container, false);
        todoList = new ArrayList<>();

        recyclerView = root.findViewById(R.id.todoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        todoAdapter = new TodoAdapter(getActivity());
        recyclerView.setAdapter(todoAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(todoAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        fab= root.findViewById(R.id.todo_fab);
        getList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTodo addTodo = new AddTodo(getContext());
                addTodo.show(getActivity().getSupportFragmentManager(),"Add Todo");
            }
        });


        return root;
    }

    public void getList() {
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Client client = task.getResult().getValue(Client.class);
                    List<ToDoNote> todoList;
                    if(client.getTodoList() == null){
                        todoList = new ArrayList<>();
                    }
                    else {
                        todoList = client.getTodoList();
                    }
                    todoAdapter.setTodoList(todoList);
                }
            }
        });
    }
/*
    public void Reseter(){
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                List<List<ToDoNote>> ToDoReseter;

                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Client client = task.getResult().getValue(Client.class);
                    if(client.getTodoReseter() == null){
                        ToDoReseter = new ArrayList<>();
                    }
                    else {
                        ToDoReseter = client.getTodoReseter();
                    }
                    ToDoReseter.add(client.getTodoList());
                    client.setTodoList(new ArrayList<ToDoNote>());
                    mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(client);
                    todoAdapter.setTodoList(client.getTodoList());
                }
            }
        });
    }*/

}