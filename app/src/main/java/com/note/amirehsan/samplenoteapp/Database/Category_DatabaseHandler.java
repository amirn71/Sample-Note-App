package com.note.amirehsan.samplenoteapp.Database;

/**
 * Created by AMiR ehsan on 2/26/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Category_DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "category";

    // Contacts table name
    private static final String TABLE_CONTACTS = "category_table";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_Color = "color";

    public Category_DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DATE + " TEXT," + KEY_Color + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new categoryContact
    public void addContact(Category_Contact categoryContact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, categoryContact.getName()); // Category_Contact Name
        values.put(KEY_DATE, categoryContact.getDate()); // Category_Contact date
        values.put(KEY_Color, categoryContact.getColor()); // Category_Contact color
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public Category_Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_DATE, KEY_Color}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category_Contact categoryContact = new Category_Contact(Integer.parseInt(cursor.getString(0))
                , cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return categoryContact
        cursor.close();
        db.close();
        return categoryContact;
    }

    // Getting All Contacts
    public List<Category_Contact> getAllContacts() {
        List<Category_Contact> categoryContactList = new ArrayList<Category_Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category_Contact categoryContact = new Category_Contact();
                categoryContact.setID(Integer.parseInt(cursor.getString(0)));
                categoryContact.setName(cursor.getString(1));
                categoryContact.setDate(cursor.getString(2));
                categoryContact.setColor(cursor.getString(3));
                // Adding categoryContact to list
                categoryContactList.add(categoryContact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return categoryContactList;
    }

    // Updating single categoryContact
    public int updateContact(Category_Contact categoryContact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, categoryContact.getName());
        values.put(KEY_DATE, categoryContact.getDate());
        values.put(KEY_Color, categoryContact.getColor());
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(categoryContact.getID())});
    }

    // Deleting single categoryContact
    public void deleteContact(Category_Contact categoryContact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(categoryContact.getID())});
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }


}