package com.note.amirehsan.samplenoteapp.Units;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by AMiR ehsan on 2/26/2017.
 */

public class DB_Backup_Restore {

    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;
        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                ret = false;
            }
        }
        return ret;
    }

    public static void exportCategoryDB(String Database_name) {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "com.note.amirehsan.samplenoteapp" + "/databases/" + Database_name;
        String backupDBPath = "/Sample_note/Backup_category.db";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Log.e("ok", backupDB.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportNoteDB(String Database_name) {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "com.note.amirehsan.samplenoteapp" + "/databases/" + Database_name;
        String backupDBPath = "/Sample_note/Backup_note.db";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Log.e("ok", backupDB.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void restoreCategoryDB(String Database_name) {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/Sample_note/Backup_category.db";
        String backupDBPath = "/data/" + "com.note.amirehsan.samplenoteapp" + "/databases/" + Database_name;
        File currentDB = new File(sd, currentDBPath);
        File backupDB = new File(data, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Log.e("ok", backupDB.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void restoreNoteDB(String Database_name) {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/Sample_note/Backup_note.db";
        String backupDBPath = "/data/" + "com.note.amirehsan.samplenoteapp" + "/databases/" + Database_name;
        File currentDB = new File(sd, currentDBPath);
        File backupDB = new File(data, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Log.e("ok", backupDB.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}