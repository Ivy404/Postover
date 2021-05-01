package com.example.postover.ui.TODO;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.postover.Model.ToDoNote;
import com.example.postover.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddTodo extends BottomSheetDialogFragment {

    private EditText newTodoText;
    private Button newTodoButton;


    public static AddTodo newInstance() {
        return new AddTodo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_new, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTodoText = getView().findViewById(R.id.new_todo_text);
        newTodoButton = getView().findViewById(R.id.new_todo_button);
        //data base

        Bundle bundle = getArguments();
        boolean isUpdated = false;
        if (bundle != null) {
            String todo = bundle.getString("Todo");
            newTodoText.setText(todo);
            isUpdated = true;
            if (todo.length() > 0) {
                newTodoButton.setTextColor(ContextCompat.getColor(getContext(), R.color.lightred));
            }
        }
        newTodoButton.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")) {
                    newTodoButton.setEnabled(false);
                    newTodoButton.setTextColor(Color.GRAY);
                } else {
                    newTodoButton.setEnabled(true);
                    newTodoButton.setTextColor(ContextCompat.getColor(getContext(), R.color.lightred));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdated = isUpdated;
        newTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textTodo = newTodoText.getText().toString();
                if (finalIsUpdated) {
                    //database
                } else {
                    ToDoNote todoNote = new ToDoNote(textTodo);

                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        FragmentActivity fragmentActivityactivity = getActivity();
        if (fragmentActivityactivity instanceof DialogCloseListener) {
            ((DialogCloseListener) fragmentActivityactivity).handleDialogClose(dialog);
        }
    }

}
