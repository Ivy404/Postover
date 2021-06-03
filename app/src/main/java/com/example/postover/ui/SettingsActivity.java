package com.example.postover.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.example.postover.MainActivity;
import com.example.postover.R;
import com.example.postover.ui.data.usernameDialog;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity implements usernameDialog.ExampleDialogListener{
    private TextView textViewUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        SharedPreferences sharedPreferences = getSharedPreferences("night", 0);
        boolean isNightModeOn = sharedPreferences.getBoolean("night_mode", false);
        ImageView backbtt = (ImageView) findViewById(R.id.backbtt);
        SwitchMaterial nightmode = findViewById(R.id.switchNightMode);

        Drawable bgfg = getResources().getDrawable(R.drawable.bgdefault);

        bgfg.setAlpha(40);
        LinearLayout layout =(LinearLayout) findViewById(R.id.setting_layout_vertical);
        layout.setBackground(bgfg);

        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            nightmode.setChecked(true);
        }

        nightmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    nightmode.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode", true);
                    editor.apply();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    nightmode.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode", false);
                    editor.apply();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.putExtra("KeepLoged","KeepLoged");
        SettingsActivity.this.finish();
        SettingsActivity.this.startActivity(intent);
    }

    public void jumper(View v){
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.putExtra("KeepLoged","KeepLoged");
        SettingsActivity.this.finish();
        SettingsActivity.this.startActivity(intent);
    }

    public void changeUsername(View v){
        usernameDialog dialog = new usernameDialog();
        dialog.show(getSupportFragmentManager(), "username");
    }

    @Override
    public void applyTexts(String username) {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.putExtra("ChangeUs",username);
        SettingsActivity.this.finish();
        SettingsActivity.this.startActivity(intent);
    }
}