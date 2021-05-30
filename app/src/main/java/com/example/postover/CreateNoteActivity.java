package com.example.postover;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.postover.Model.Client;
import com.example.postover.Model.HomeNote;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText noteTile, textNote;
    private ImageView imageSave, backimage,imageNote;
    private FloatingActionButton addPhoto,addUbication;
    private FloatingActionsMenu floatingActionsMenu;
    private TextView textDataTime;
    private LinearLayout linearLayout;
    private  HomeNote note;
    private List<String> images,texts,imagesToDelete,imagesDeleted;

    private  int position=-1,access=0;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 10;
    private static final int REQUEST_CODE_SELECT_IMAGE = 11;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private View.OnLongClickListener myClick;
    int tag = 3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note_activity);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        init();
        catchNote();

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
                deleteImages((ArrayList<String>) imagesToDelete);
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
                            if (position == -1) {
                                HomeNote homeNote = new HomeNote(noteTile.getText().toString());
                                homeNote.setText(textNote.getText().toString());
                                homeNote.setImages((ArrayList<String>) images);
                                saveTexts();
                                homeNote.setTexts(texts);
                                homeNoteList.add(homeNote);
                            } else {
                                HomeNote homeNote = homeNoteList.get(position);
                                homeNote.setTitle(noteTile.getText().toString());
                                homeNote.setText(textNote.getText().toString());
                                homeNote.setImages((ArrayList<String>) images);
                                saveTexts();
                                homeNote.setTexts(texts);
                                //homeNoteList.add(homeNote);
                            }

                            deleteImages((ArrayList<String>) imagesDeleted);
                            mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).
                                    child("homeNoteList").setValue(homeNoteList);

                        }
                        setResult(1);
                        finish();
                    }
                });


            }
        });
        myClick = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateNoteActivity.this);
                builder.setTitle("Delete Image");
                builder.setMessage("Are you sure you want to delete this Image?");
                builder.setCancelable(false);
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                int itr = 0;
                                int suma = 3;
                                while((int) v.getTag() != suma){
                                    suma+=2;
                                    itr++;
                                }
                                int index = (int) v.getTag() -3 - itr;
                                Toast.makeText(CreateNoteActivity.this,"index "+index,Toast.LENGTH_SHORT).show();

                                String url = images.get(index);
                                images.remove(index);
                                images.removeAll(Collections.singletonList(null));
                                imagesDeleted.add(url);
                                Object tagText;
                                tagText = (Object) (((int)v.getTag() ) + 1);
                                EditText etext = (EditText) linearLayout.findViewWithTag(tagText);
                                String text = etext.getText().toString();
                                if(index !=0) {
                                    texts.set(index-1,texts.get(index-1)+"\n"+text);
                                    tagText = (Object) (((int)v.getTag() ) - 1);
                                    EditText editText = (EditText)linearLayout.findViewWithTag(tagText);
                                    editText.setText(editText.getText()+"\n"+text);
                                }
                                else {
                                    textNote.setText(textNote.getText().toString()+"\n"+texts.get(index));
                                }
                                texts.remove(index);
                                texts.removeAll(Collections.singletonList(null));
                                linearLayout.removeView(linearLayout.findViewWithTag(v.getTag()));
                                 tagText = (Object) (((int)v.getTag() ) + 1);
                                linearLayout.removeView(linearLayout.findViewWithTag(tagText));
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        };


    }

    private void init() {
        try {
            if (getIntent().getExtras().getInt("position") != -1) {
                position = getIntent().getExtras().getInt("position");
            }
        }catch (NullPointerException e){}

        linearLayout = findViewById(R.id.linearlayouthomenote);

        noteTile = findViewById(R.id.noteTitle);
        textNote = findViewById(R.id.textNote);
        imageSave = findViewById(R.id.imageSave);
        textDataTime = findViewById(R.id.textDataTime);
        backimage = findViewById(R.id.imageBack);

        //imageNote = new ImageView(this);
        //imageNote.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        imagesToDelete = new ArrayList<>();
        imagesDeleted = new ArrayList<>();

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
            note = (HomeNote) bundle.getSerializable("note");
            noteTile.setText(note.getTitle());
            images = note.getImages();
            textDataTime.setText(note.getCreationDate().toString());
            textNote.setText(note.getText());
            texts = note.getTexts();
            if(texts == null) texts = new ArrayList<>();
            if(images == null) images = new ArrayList<>();

            for(String i : images){
                getImage(i);
                }
            }else{
                if(texts == null) texts = new ArrayList<>();
                if(images == null) images = new ArrayList<>();
            }
        }

    private void getImage(String image) {
        // Create a reference with an initial file path and name
        StorageReference pathReference = null;
        try {
             pathReference = storageReference.child(image);//child(mAuth.getCurrentUser().getUid()).child(image);

           pathReference.getBytes((long)(2* Math.pow(10,7))).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                ImageView imageView = new ImageView(getApplicationContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(layoutParams);

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView.setImageBitmap(bitmap);
                imageView.setTag(tag);
                tag++;
                imageView.setOnLongClickListener(myClick);
                imageView.setVisibility(View.VISIBLE);
                imageView.getLayoutParams().height = 750;
                linearLayout.addView(imageView);
                addText();


            }
        });
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void addText() {
            EditText editText = new EditText(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            editText.setLayoutParams(layoutParams);
            editText.setText(texts.get(access));
            editText.setTag(tag);
            tag++;
            access++;
            editText.setBackground(null);
            editText.setVisibility(View.VISIBLE);
            editText.setHint("You can keep writing here!");
            linearLayout.addView(editText);

    }

    private void saveTexts() {
        int itr = 0;
        for (int i = 2; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof EditText) {
                EditText editText = (EditText) linearLayout.getChildAt(i);
                texts.set(itr, editText.getText().toString());
                itr++;

            }

        }
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_CODE_SELECT_IMAGE);


    }

    private void deleteImages(ArrayList<String> imagesToDelete){
        for (String s : imagesToDelete) {
            StorageReference imageToDelete = storage.getReference(s);
            imageToDelete.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });

        }

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
                data.getDataString();
                if(selectedImageUri != null){
                    try{
                        InputStream inputStream  = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                        ImageView imageView = new ImageView(this);
                        imageView.setImageBitmap(bitmap);
                        imageView.setLayoutParams(layoutParams);
                        imageView.getLayoutParams().height = 750;
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setTag(tag);
                        tag++;
                        imageView.setOnLongClickListener(myClick);
                        linearLayout.addView(imageView);
                        images.add(uploadPicture(selectedImageUri));

                        EditText editText = new EditText(getApplicationContext());

                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        editText.setLayoutParams(layoutParams1);
                        editText.setText("");
                        editText.setTag(tag);
                        tag++;
                        editText.setBackground(null);
                        editText.setVisibility(View.VISIBLE);
                        editText.setHint("You can keep writing here!");
                        editText.setGravity(Gravity.LEFT | Gravity.TOP);
                        texts.add(editText.getText().toString());
                        linearLayout.addView(editText);



                    }catch (Exception e){
                        Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();

                    }

                }

            }
        }
    }

    private String uploadPicture(Uri imageUri) {
        final String randomKey = UUID.randomUUID().toString();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading image..");
        pd.show();


        StorageReference ImagesRef = storageReference.child("images").child(mAuth.getCurrentUser().getUid()).child(randomKey);
        ImagesRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                imagesToDelete.add("images/"+mAuth.getCurrentUser().getUid()+"/"+randomKey);
                Snackbar.make(findViewById(android.R.id.content),"Image Upload.",Snackbar.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Upload Failed!",Toast.LENGTH_LONG);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                pd.setMessage("Progress: "+(int)progressPercent+"%");
            }
        });

        return "images/"+mAuth.getCurrentUser().getUid()+"/"+randomKey;

    }
}