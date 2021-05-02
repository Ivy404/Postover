package com.example.postover;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.postover.Model.Client;
import com.example.postover.Model.HomeNote;
import com.example.postover.SlideFragments.AdapterSlide;
import com.example.postover.ui.ActivityLogin;
import com.example.postover.ui.ActivityRegister;
import com.example.postover.ui.DialogCloseListener;
import com.example.postover.ui.TODO.TodoFragment;
import com.example.postover.ui.home.HomeFragment;
import com.example.postover.ui.CALENDAR.CalendarFragment;
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
import com.google.firebase.database.DataSnapshot;
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


public class MainActivity extends AppCompatActivity implements DialogCloseListener {


    private AppBarConfiguration mAppBarConfiguration;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;



    private TodoFragment todoFragment;
    private HomeFragment homeFragment;
    private CalendarFragment calendarFragment;


    //cositas del firebase
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();

        try {
            if (getIntent().getExtras().getString("Login") != null) {
                View v = new View(getApplicationContext());
                Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                MainActivity.this.finish();
                MainActivity.this.startActivity(intent);
            } else if (getIntent().getExtras().getString("Guest") != null) {
                System.out.println("he entrado");

            } else if (getIntent().getExtras().getString("KeepLoged") != null) {
                createFragments();
            } else {
                createFragments();
            }
        } catch (NullPointerException e) {
            //createFragments();
        }


    }

    public void signOut(View v) {
        mAuth.signOut();
        Intent mainIntent = new Intent(MainActivity.this, ActivityRegister.class);
        MainActivity.this.startActivity(mainIntent);
        MainActivity.this.finish();
    }

    public void createFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        todoFragment = new TodoFragment();
        fragmentList.add(todoFragment);
        homeFragment = new HomeFragment();
        fragmentList.add(homeFragment);
        calendarFragment =new CalendarFragment();
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
        updateNav();
    }
    public void updateNav(){
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Client client = task.getResult().getValue(Client.class);
                    TextView nameNavHead = (TextView) findViewById(R.id.name_navhead);
                    nameNavHead.setText(client.getName());
                    TextView emailNavHead = (TextView) findViewById(R.id.emailNavhead);
                    emailNavHead.setText(client.getMail());

                }
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



    public void openDrawer(View v) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }


    @Override
    public void handleDialogClose(DialogInterface dialog,String note) {
       switch (note){
           case "Todo":
               todoFragment.getList();
               break;
           case "HomeNote":
                homeFragment.getList();
               break;
           case "CalendarNote":
               break;

       }
    }

}