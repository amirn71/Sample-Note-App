package com.note.amirehsan.samplenoteapp.Activityes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.note.amirehsan.samplenoteapp.Database.Note_Contact;
import com.note.amirehsan.samplenoteapp.Database.Note_DatabaseHandler;
import com.note.amirehsan.samplenoteapp.Units.Persian_date;
import com.note.amirehsan.samplenoteapp.R;

import jp.wasabeef.richeditor.RichEditor;


public class Activity_Note_Writing extends AppCompatActivity {

    RichEditor editor;
    EditText title;
    Button bold, italic, underline, leftJustify, rightJustify, centerJustify, strikethrough, superscript;
    Typeface fontAwesome;
    boolean exist = false;
    Note_DatabaseHandler db;
    String writeNoteCategoryName, toolbarColor;
    String exist_id = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializing_contents();

        // Receiving the Data
        Intent intent = getIntent();
        toolbarColor = intent.getStringExtra("color");
        writeNoteCategoryName = intent.getStringExtra("category");
        exist_id = intent.getStringExtra("id");

        if (exist_id.equals("null")) {
            exist = false;
        } else if (!exist_id.equals("null")) {
            exist = true;
            title.setText(intent.getStringExtra("title"));
            editor.setHtml(intent.getStringExtra("data"));
        }

        if (toolbarColor.equals("green")) {
            toolbar.setBackgroundResource(R.color.green);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.parseColor("#469f4a"));
            }

        } else if (toolbarColor.equals("red")) {
            toolbar.setBackgroundResource(R.color.red);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.parseColor("#df3d31"));
            }

        } else if (toolbarColor.equals("blue")) {

            toolbar.setBackgroundResource(R.color.blue);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.parseColor("#1d8be2"));
            }

        } else if (toolbarColor.equals("purple")) {
            toolbar.setBackgroundResource(R.color.purple);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.parseColor("#8c239e"));
            }
        }

        bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setBold();
            }
        });

        italic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setItalic();
            }
        });

        underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setUnderline();
            }
        });

        centerJustify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setAlignCenter();
            }
        });

        leftJustify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setAlignLeft();
            }
        });

        rightJustify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setAlignRight();
            }
        });

        strikethrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setStrikeThrough();
            }
        });

        superscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setSuperscript();
            }
        });
    }

    public void initializing_contents() {

        fontAwesome = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");
        bold = (Button) findViewById(R.id.bold);
        italic = (Button) findViewById(R.id.italic);
        underline = (Button) findViewById(R.id.underline);
        underline.setPaintFlags(underline.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        centerJustify = (Button) findViewById(R.id.centerJustify);
        leftJustify = (Button) findViewById(R.id.left_justify);
        rightJustify = (Button) findViewById(R.id.right_justify);
        strikethrough = (Button) findViewById(R.id.strikethrough);
        superscript = (Button) findViewById(R.id.superscript);
        centerJustify.setTypeface(fontAwesome);
        leftJustify.setTypeface(fontAwesome);
        rightJustify.setTypeface(fontAwesome);
        strikethrough.setTypeface(fontAwesome);
        superscript.setTypeface(fontAwesome);
        editor = (RichEditor) findViewById(R.id.editText);
        title = (EditText) findViewById(R.id.title);
        title.setText("");
        db = new Note_DatabaseHandler(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case android.R.id.home:

                if (!exist) {
                    Time now = new Time();
                    now.setToNow();
                    Note_Contact noteContact = new Note_Contact(title.getText().toString(),
                            writeNoteCategoryName, editor.getHtml(),
                            Persian_date.getCurrentShamsidate() + " - " + now.format("%k:%M"),
                            toolbarColor, "no");
                    db.addContact(noteContact);
                    Intent nextScreen = new Intent(this, Activity_Add_Note.class);
                    nextScreen.putExtra("color", toolbarColor);
                    nextScreen.putExtra("category", writeNoteCategoryName);
                    startActivity(nextScreen);
                    finish();
                } else if (exist) {
                    Time now = new Time();
                    now.setToNow();
                    Note_Contact noteContactFromID = db.getContact(Integer.parseInt(exist_id));
                    Note_Contact noteContact = new Note_Contact(noteContactFromID.getID(), title.getText().toString(),
                            writeNoteCategoryName, editor.getHtml(),
                            Persian_date.getCurrentShamsidate() + " - " + now.format("%k:%M"),
                            toolbarColor, "no");
                    db.updateContact(noteContact);
                    Intent nextScreen = new Intent(this, Activity_Add_Note.class);
                    nextScreen.putExtra("color", toolbarColor);
                    nextScreen.putExtra("category", writeNoteCategoryName);
                    startActivity(nextScreen);
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (!exist) {
            Time now = new Time();
            now.setToNow();
            Note_Contact noteContact = new Note_Contact(title.getText().toString(),
                    writeNoteCategoryName, editor.getHtml(),
                    Persian_date.getCurrentShamsidate() + " - " + now.format("%k:%M"),
                    toolbarColor, "no");
            db.addContact(noteContact);
            Intent nextScreen = new Intent(this, Activity_Add_Note.class);
            nextScreen.putExtra("color", toolbarColor);
            nextScreen.putExtra("category", writeNoteCategoryName);
            startActivity(nextScreen);
            finish();
        } else if (exist) {
            Time now = new Time();
            now.setToNow();
            Note_Contact noteContactFromID = db.getContact(Integer.parseInt(exist_id));
            Note_Contact noteContact = new Note_Contact(noteContactFromID.getID(), title.getText().toString(),
                    writeNoteCategoryName, editor.getHtml(),
                    Persian_date.getCurrentShamsidate() + " - " + now.format("%k:%M"),
                    toolbarColor, "no");
            db.updateContact(noteContact);
            Intent nextScreen = new Intent(this, Activity_Add_Note.class);
            nextScreen.putExtra("color", toolbarColor);
            nextScreen.putExtra("category", writeNoteCategoryName);
            startActivity(nextScreen);
            finish();
        }
        super.onBackPressed();
    }
}
