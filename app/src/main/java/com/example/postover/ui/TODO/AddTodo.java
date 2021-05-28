package com.example.postover.ui.TODO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;


import com.example.postover.Model.Client;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;
import com.example.postover.ui.DialogCloseListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AddTodo extends DialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTodoText;
    private Button newTodoButton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return createDialog();
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_todo_new,null);
        builder.setView(v);

        newTodoText = v.findViewById(R.id.new_todo_text);
        newTodoButton = v.findViewById(R.id.new_todo_button);
        newTodoButton.setEnabled(false);
        newTodoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newTodoButton.setEnabled(false);
                    newTodoButton.setTextColor(Color.GRAY);
                }
                else{
                    newTodoButton.setEnabled(true);
                    newTodoButton.setTextColor(ContextCompat.getColor(getContext(),R.color.lightred));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            todoList.add(new ToDoNote(newTodoText.getText().toString()));
                            mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(client);

                        }
                        dismiss();
                    }

                });



            }
        });

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    public AddTodo(Context context) {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog,"Todo");
        }
    }

}


/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_todo_new,container,false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }
*/
    /*
    @Override
     public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        newTodoText = getView().findViewById(R.id.new_todo_text);
        newTodoButton = getView().findViewById(R.id.new_todo_button);
        newTodoButton.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        newTodoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newTodoButton.setEnabled(false);
                    newTodoButton.setTextColor(Color.GRAY);
                }
                else{
                    newTodoButton.setEnabled(true);
                    newTodoButton.setTextColor(ContextCompat.getColor(getContext(),R.color.lightred));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            todoList.add(new ToDoNote(newTodoText.getText().toString()));
                            mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(client);

                        }
                        dismiss();
                    }

                });



            }
        });
    }*/


