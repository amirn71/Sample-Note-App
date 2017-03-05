package com.note.amirehsan.samplenoteapp.Activityes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import com.note.amirehsan.samplenoteapp.Database.Category_DatabaseHandler;
import com.note.amirehsan.samplenoteapp.R;

public class Activity_Add_Note extends AppCompatActivity {

    Category_DatabaseHandler db;

    public String color, categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        setContentView(R.layout.activity_take_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Receiving the Data
        Intent colorIntent = getIntent();
        Intent nameIntent = getIntent();
        color = colorIntent.getStringExtra("color");
        categoryName = nameIntent.getStringExtra("category");

        getSupportActionBar().setTitle(categoryName);

        if (color.equals("green")) {
            toolbar.setBackgroundResource(R.color.green);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.parseColor("#469f4a"));
            }

        } else if (color.equals("red")) {
            toolbar.setBackgroundResource(R.color.red);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.parseColor("#df3d31"));
            }

        } else if (color.equals("blue")) {

            toolbar.setBackgroundResource(R.color.blue);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.parseColor("#1d8be2"));
            }

        } else if (color.equals("purple")) {
            toolbar.setBackgroundResource(R.color.purple);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.parseColor("#8c239e"));
            }
        }

        db = new Category_DatabaseHandler(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        Intent nextScreen = new Intent(this, Activity_Category.class);
        nextScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(nextScreen);
        finish();
    }
}
