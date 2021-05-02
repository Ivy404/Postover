package com.example.postover.ui.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.postover.Model.Client;
import com.example.postover.Model.HomeNote;
import com.example.postover.Model.HomeNote;
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

public class AddHomeNote extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newHomeNoteText;
    private Button newHomeNoteButton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    public static AddHomeNote newInstance(){
        return new AddHomeNote();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_homenote_new,container,false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        newHomeNoteText = getView().findViewById(R.id.new_note_text);
        newHomeNoteButton = getView().findViewById(R.id.new_note_button);
        newHomeNoteButton.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        newHomeNoteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newHomeNoteButton.setEnabled(false);
                    newHomeNoteButton.setTextColor(Color.GRAY);
                }
                else{
                    newHomeNoteButton.setEnabled(true);
                    newHomeNoteButton.setTextColor(ContextCompat.getColor(getContext(),R.color.lightred));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newHomeNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get client
                mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        } else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            Client client = task.getResult().getValue(Client.class);
                            List<HomeNote> homeNoteList;
                            if(client.getHomeNoteList() == null){
                                homeNoteList = new ArrayList<>();
                            }
                            else {
                                homeNoteList = client.getHomeNoteList();
                            }
                            homeNoteList.add(new HomeNote(newHomeNoteText.getText().toString()));
                            mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(client);

                        }
                        dismiss();
                    }

                });



            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog,"HomeNote");
        }
    }

}
