package com.example.postover.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.example.postover.MainActivity;
import com.example.postover.R;
import com.example.postover.ui.data.usernameDialog;

public class SettingsActivity extends AppCompatActivity implements usernameDialog.ExampleDialogListener{
    private TextView textViewUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        ImageView backbtt = (ImageView) findViewById(R.id.backbtt);
        Drawable bgfg = getResources().getDrawable(R.drawable.bgdefault);
        bgfg.setAlpha(40);
        LinearLayout layout =(LinearLayout) findViewById(R.id.setting_layout_vertical);
        layout.setBackground(bgfg);

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

    }
}