package com.note.amirehsan.samplenoteapp.Activityes;

import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.note.amirehsan.samplenoteapp.Units.DB_Backup_Restore;
import com.note.amirehsan.samplenoteapp.Database.Category_DatabaseHandler;
import com.note.amirehsan.samplenoteapp.Database.Note_DatabaseHandler;
import com.note.amirehsan.samplenoteapp.R;

public class Activity_Category extends AppCompatActivity {

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    private FragmentRefreshListener fragmentRefreshListener;

    Note_DatabaseHandler dbNote;
    Category_DatabaseHandler dbCategeory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        } else if (permission == PackageManager.PERMISSION_GRANTED) {
            DB_Backup_Restore.createDirIfNotExists("/Sample_note/");
        }

        dbNote = new Note_DatabaseHandler(getApplicationContext());
        dbCategeory = new Category_DatabaseHandler(getApplicationContext());

        FragmentManager fm = getFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getFragmentManager().getBackStackEntryCount() == 0) finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.backup) {

            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                makeRequest();
            } else if (permission == PackageManager.PERMISSION_GRANTED) {
                DB_Backup_Restore.createDirIfNotExists("/Sample_note/");
                DB_Backup_Restore.exportCategoryDB(dbCategeory.getDatabaseName());
                DB_Backup_Restore.exportNoteDB(dbNote.getDatabaseName());
                Toast.makeText(this, R.string.backup, Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        if (id == R.id.restore) {
            DB_Backup_Restore.restoreCategoryDB(dbCategeory.getDatabaseName());
            DB_Backup_Restore.restoreNoteDB(dbNote.getDatabaseName());
            Toast.makeText(this, R.string.successful, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getFragmentRefreshListener() != null) {
                        getFragmentRefreshListener().onRefresh();
                    }
                }
            }, 1000);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static final int RECORD_REQUEST_CODE = 101;

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, RECORD_REQUEST_CODE);
        DB_Backup_Restore.createDirIfNotExists("/Sample_note/");
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {

            finish();

            super.onStop();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public interface FragmentRefreshListener {
        void onRefresh();
    }
}
