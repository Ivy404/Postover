package com.example.postover;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.postover.Model.Client;
import com.example.postover.SlideFragments.AdapterSlide;
import com.example.postover.ui.TODO.TodoFragment;
import com.example.postover.ui.home.HomeFragment;
import com.example.postover.ui.slideshow.CalendarFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity   {


    private AppBarConfiguration mAppBarConfiguration;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText editTextUsername, editTextPassword, editTextEmail, editTextName;

    private EditText loginMail, loginPassword;
    private String email, name, username, password;
    private String mailLogin, passwordLogin;

    //cositas del firebase
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();




        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new TodoFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new CalendarFragment());
        AdapterSlide adapter = new AdapterSlide(getSupportFragmentManager(), getLifecycle(), fragmentList);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager2);
        viewPager2.setAdapter(adapter);
        viewPager2.setCurrentItem(1);

        Button todo = findViewById(R.id.todo);
        Button notes = findViewById(R.id.notes);
        Button calendar = findViewById(R.id.calendar);

        todo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                viewPager2.setCurrentItem(0);
           }
       });;
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(1);
            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(2);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void createLoginDialog(View v) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View loginPopupView = getLayoutInflater().inflate(R.layout.popup_login, null);


        Button login = (Button) loginPopupView.findViewById(R.id.btn_login);
        loginMail = (EditText) loginPopupView.findViewById(R.id.pt_username);
        loginPassword = (EditText) loginPopupView.findViewById(R.id.pt_password);


        dialogBuilder.setView(loginPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailLogin = loginMail.getText().toString();
                passwordLogin = loginPassword.getText().toString();
                loginUser();
            }
        });

    }



    public void openDrawer(View v){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    public void createRegisterDialog(View v){
        dialogBuilder = new AlertDialog.Builder(this);
        final View registerPopupView = getLayoutInflater().inflate(R.layout.popup_register, null);
        editTextUsername = (EditText) registerPopupView.findViewById(R.id.pt_usernameRegister);
        editTextName = (EditText) registerPopupView.findViewById(R.id.pt_fullnameRegister);
        editTextPassword = (EditText) registerPopupView.findViewById(R.id.pt_TextPassword);
        editTextEmail = (EditText) registerPopupView.findViewById(R.id.pt_mailRegister);
        Button register = (Button) registerPopupView.findViewById(R.id.btn_registerRegister);

        dialogBuilder.setView(registerPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

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
                        Toast.makeText(MainActivity.this, "Password isn't strong enough", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
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
                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("users").child(id).setValue(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "User added", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(MainActivity.this, "User could not be created", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void loginUser() {
        mAuth.signInWithEmailAndPassword(mailLogin, passwordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(MainActivity.this, "Success! login completed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Error! These credentials do not match our records", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}