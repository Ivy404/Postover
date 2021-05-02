package com.example.postover.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.postover.MainActivity;
import com.example.postover.Model.Client;
import com.example.postover.Model.ToDoNote;
import com.example.postover.R;
import com.example.postover.Splash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ActivityRegister extends AppCompatActivity {
    private String email, name, username, password;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editTextUsername, editTextPassword, editTextEmail, editTextName;
        editTextUsername = (EditText) findViewById(R.id.pt_usernameRegister);
        editTextName = (EditText) findViewById(R.id.pt_fullnameRegister);
        editTextPassword = (EditText) findViewById(R.id.pt_TextPassword);
        editTextEmail = (EditText) findViewById(R.id.pt_mailRegister);
        Button register = (Button) findViewById(R.id.button_register_final);
        TextView member = (TextView) findViewById(R.id.TV_member);
        TextView guest = (TextView) findViewById(R.id.guestMember);
        intent = new Intent(ActivityRegister.this, MainActivity.class);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextName.getText().toString();
                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();
                email = editTextEmail.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    if (password.length() >= 6) {
                        registerNewUser();
                    } else {
                        Toast.makeText(ActivityRegister.this, "Error! Password isn't strong enough", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityRegister.this, "Error! Complete all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(ActivityRegister.this, MainActivity.class);
                mainIntent.putExtra("Login","Login");
                ActivityRegister.this.startActivity(mainIntent);
                ActivityRegister.this.finish();
            }
        });
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerGuestUser();
                Intent mainIntent = new Intent(ActivityRegister.this, MainActivity.class);
                mainIntent.putExtra("Guest","Guest");
                ActivityRegister.this.startActivity(mainIntent);
                ActivityRegister.this.finish();
            }
        });
    }
    private void registerGuestUser() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ActivityRegister.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void registerNewUser() {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Client cliente = new Client(name, password, email, username);
                    String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    mDatabase.child("users").child(id).setValue(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(ActivityRegister.this, "Success! User added", Toast.LENGTH_LONG).show();
                                Intent mainIntent = new Intent(ActivityRegister.this, MainActivity.class);
                                mainIntent.putExtra("KeepLoged","KeepLoged");
                                ActivityRegister.this.startActivity(mainIntent);
                                ActivityRegister.this.finish();
                            } else {
                                Toast.makeText(ActivityRegister.this, "Error! User could not be created", Toast.LENGTH_SHORT).show();
                                mDatabase.child("users").child(id).removeValue();
                                Intent mainIntent = new Intent(ActivityRegister.this, ActivityRegister.class);
                                ActivityRegister.this.startActivity(mainIntent);
                                ActivityRegister.this.finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ActivityRegister.this, "Error! Something went wrong", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(ActivityRegister.this, ActivityRegister.class);
                    ActivityRegister.this.startActivity(mainIntent);
                    ActivityRegister.this.finish();
                }
            }
        });

    }
}


