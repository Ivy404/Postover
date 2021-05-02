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

public class ActivityLogin extends AppCompatActivity {
    private EditText loginMail, loginPassword;
    private String mailLogin, passwordLogin;
    private final int GOOGLE_SIGN_IN = 100;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public FirebaseUser user;
    Intent activityIntent = new Intent(ActivityLogin.this, MainActivity.class);

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
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignInClient googleClient = GoogleSignIn.getClient(ActivityLogin.this, gso);
                googleClient.signOut();

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
                                //Datos realetime database
                            }else{
                                Toast.makeText(ActivityLogin.this, "Error! Google authentification exploted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (ApiException e) {
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
                    finish();
                    startActivity(activityIntent);
                } else {
                    Toast.makeText(ActivityLogin.this, "Error! These credentials do not match our records", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}