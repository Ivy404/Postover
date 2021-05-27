package com.example.postover;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.postover.Model.Client;
import com.example.postover.Model.HomeNote;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText noteTile, textNote;
    private ImageView imageSave, backimage,imageNote;
    private FloatingActionButton addPhoto,addUbication;
    private FloatingActionsMenu floatingActionsMenu;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private  int position=-1;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 10;
    private static final int REQUEST_CODE_SELECT_IMAGE = 11;


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
                setResult(1);
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
        imageNote = findViewById(R.id.imageNote);
        floatingActionsMenu = findViewById(R.id.menuFloating);
        addPhoto = (com.getbase.floatingactionbutton.FloatingActionButton) floatingActionsMenu.getChildAt(0);
        addUbication = (com.getbase.floatingactionbutton.FloatingActionButton) floatingActionsMenu.getChildAt(1);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(CreateNoteActivity.this,new String[]
                                {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_STORAGE_PERMISSION
                        );
                }else{
                    selectImage();
                }
            }
        });
        addUbication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_CODE_SELECT_IMAGE);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_STORAGE_PERMISSION && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }
            else{
                Toast.makeText(this,"Permission Denied!",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if(data!=null){
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null){
                    try{
                        InputStream inputStream  = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageNote.setImageBitmap(bitmap);
                        imageNote.setVisibility(View.VISIBLE);

                    }catch (Exception e){
                        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }
    }
}