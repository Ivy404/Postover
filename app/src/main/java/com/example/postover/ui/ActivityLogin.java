package com.example.postover.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.postover.MainActivity;
import com.example.postover.Model.Client;
import com.example.postover.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ActivityLogin extends AppCompatActivity {
    private EditText loginMail, loginPassword;
    private String mailLogin, passwordLogin;
    private final int GOOGLE_SIGN_IN = 100;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();


        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();

        Button login = (Button) findViewById(R.id.btn_login);
        TextView register = findViewById(R.id.tv_register);
        ImageView googleLogin = findViewById(R.id.google_login);
        loginMail = (EditText) findViewById(R.id.pt_username);
        loginPassword = (EditText) findViewById(R.id.pt_password);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailLogin = loginMail.getText().toString();
                passwordLogin = loginPassword.getText().toString();
                if (mailLogin.length() > 0 && passwordLogin.length() > 0) {
                    loginUser();
                } else {
                    Toast.makeText(ActivityLogin.this, "Cannot be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(ActivityLogin.this, ActivityRegister.class);
                ActivityLogin.this.finish();
                ActivityLogin.this.startActivity(mainIntent);

            }
        });

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("1031976145011-f922paqe06o95756njnsv9mkvtc50n5v.apps.googleusercontent.com")
                        .requestEmail()
                        .build();

                GoogleSignInClient googleClient = GoogleSignIn.getClient(ActivityLogin.this, gso);


                //googleClient.signOut();

                startActivityForResult(googleClient.getSignInIntent(), GOOGLE_SIGN_IN);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                if(account != null){
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);

                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                Client cliente = new Client(user.getDisplayName(), user.getEmail());

                                String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                mDatabase.child("users").child(id).setValue(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task2) {
                                        if (task2.isSuccessful()) {
                                            Toast.makeText(ActivityLogin.this, "Success! User added", Toast.LENGTH_LONG).show();
                                            Intent mainIntent = new Intent(ActivityLogin.this, MainActivity.class);
                                            mainIntent.putExtra("KeepLoged","KeepLoged");
                                            ActivityLogin.this.startActivity(mainIntent);
                                            ActivityLogin.this.finish();
                                        } else {
                                            Toast.makeText(ActivityLogin.this, "Error! User could not be created", Toast.LENGTH_SHORT).show();
                                            mDatabase.child("users").child(id).removeValue();
                                            Intent mainIntent = new Intent(ActivityLogin.this, ActivityLogin.class);
                                            ActivityLogin.this.startActivity(mainIntent);
                                            ActivityLogin.this.finish();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(ActivityLogin.this, "Error! Google authentification exploted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (ApiException e) {
                System.out.println(e.getMessage());
                Toast.makeText(this, "Error! Google authentification exploted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loginUser() {
        mAuth.signInWithEmailAndPassword(mailLogin, passwordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = mAuth.getCurrentUser();
                    Toast.makeText(ActivityLogin.this, "Success! login completed", Toast.LENGTH_SHORT).show();
                    Intent activityIntent = new Intent(ActivityLogin.this, MainActivity.class);
                    activityIntent.putExtra("KeepLoged","KeepLoged");
                    ActivityLogin.this.finish();
                    ActivityLogin.this.startActivity(activityIntent);
                } else {
                    Toast.makeText(ActivityLogin.this, "Error! These credentials do not match our records", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}