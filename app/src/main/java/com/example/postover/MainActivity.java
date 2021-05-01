package com.example.postover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
        // TextView register = findViewById(R.id.tv_register);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View loginPopupView = getLayoutInflater().inflate(R.layout.popup_login, null);
        Button login = (Button) loginPopupView.findViewById(R.id.btn_login);
        TextView register = loginPopupView.findViewById(R.id.tv_register);
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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.postover.ui.ActivityRegister.class);
                startActivity(intent);
            }
        });

    }


    public void openDrawer(View v){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
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