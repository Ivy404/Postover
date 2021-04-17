package com.example.postover;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.postover.onboarding.AdapterSlide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity   {

    private AppBarConfiguration mAppBarConfiguration;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText username,password,mail,name;
    private Button register;
    private ViewPager2 viewPager2;
    private AdapterSlide pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
       // NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow) .setDrawerLayout(drawer).build();
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.viewPagerFragment) .setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);






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

    public void createLoginDialog(View v){
        dialogBuilder = new AlertDialog.Builder(this);
        final View loginPopupView = getLayoutInflater().inflate(R.layout.popup_login, null);

        dialogBuilder.setView(loginPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

    }

    public void createRegisterDialog(View v){
        dialogBuilder = new AlertDialog.Builder(this);
        final View registerPopupView = getLayoutInflater().inflate(R.layout.popup_register, null);
        username = (EditText) registerPopupView.findViewById(R.id.pt_usernameRegister);
        name = (EditText) registerPopupView.findViewById(R.id.pt_fullnameRegister);
        password = (EditText) registerPopupView.findViewById(R.id.pt_TextPassword);
        mail = (EditText) registerPopupView.findViewById(R.id.pt_mailRegister);

        register = (Button) registerPopupView.findViewById(R.id.btn_registerRegister);

        dialogBuilder.setView(registerPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser(){

    }

}