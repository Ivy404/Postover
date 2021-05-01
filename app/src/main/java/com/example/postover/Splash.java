package com.example.postover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.postover.ui.ActivityRegister;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private FirebaseAuth mAuth;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            Intent mainIntent = new Intent(Splash.this, MainActivity.class);
            Splash.this.startActivity(mainIntent);
            Splash.this.finish();

        }else{
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(Splash.this, ActivityRegister.class);
                    mainIntent.putExtra("KeepLoged","KeepLoged");
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);


        }
    }
}