package com.example.postover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.postover.Model.Client;
import com.example.postover.Model.HomeNote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText noteTile, textNote;
    private ImageView imageSave, backimage;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private  int position=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note_activity);

        init();
        catchNote();


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        noteTile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    imageSave.setEnabled(false);

                } else {
                    imageSave.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageSave.setOnClickListener(new View.OnClickListener() {
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
                            if (client.getHomeNoteList() == null) {
                                homeNoteList = new ArrayList<>();
                            } else {
                                homeNoteList = client.getHomeNoteList();
                            }
                            if(position==-1) {
                                HomeNote homeNote = new HomeNote(noteTile.getText().toString());
                                homeNote.setText(textNote.getText().toString());
                                homeNoteList.add(homeNote);
                            }
                            else{
                                HomeNote homeNote = homeNoteList.get(position);
                                homeNote.setTitle(noteTile.getText().toString());
                                homeNote.setText(textNote.getText().toString());
                            }
                            mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(client);
                        }
                        setResult(1);
                        finish();
                    }
                });


            }
        });

    }

    private void init() {
        try {
            if (getIntent().getExtras().getInt("position") != -1) {
                position = getIntent().getExtras().getInt("position");
            }
        }catch (NullPointerException e){}
        noteTile = findViewById(R.id.noteTitle);
        textNote = findViewById(R.id.textNote);
        imageSave = findViewById(R.id.imageSave);
        backimage = findViewById(R.id.imageBack);


    }

    private void catchNote() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            //Se obtiene el la nota del objeto Bundle y se ñadden los valores a la interfaz
            HomeNote note = (HomeNote) bundle.getSerializable("note");
            noteTile.setText(note.getTitle());

            textNote.setText(note.getText());


        }
    }
}