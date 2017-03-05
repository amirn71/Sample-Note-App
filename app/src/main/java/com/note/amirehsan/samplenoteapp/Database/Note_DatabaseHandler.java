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

public class Note_DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "note";

    // Contacts table name
    private static final String TABLE_CONTACTS = "note_table";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_Category = "category";
    private static final String KEY_DATA = "data";
    private static final String KEY_DATE = "date";
    private static final String KEY_Color = "color";
    private static final String KEY_Draw = "activity_draw";

    public Note_DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_Category + " TEXT," + KEY_DATA + " TEXT,"
                + KEY_DATE + " TEXT," + KEY_Color + " TEXT," + KEY_Draw + " TEXT" + ")";
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

    // Adding new note_Contact
    public void addContact(Note_Contact noteContact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, noteContact.getName()); // Contact Name
        values.put(KEY_Category, noteContact.getCategory()); // Contact cat
        values.put(KEY_DATA, noteContact.getData()); // Contact data
        values.put(KEY_DATE, noteContact.getDate()); // Contact date
        values.put(KEY_Color, noteContact.getColor()); // Contact color
        values.put(KEY_Draw, noteContact.getDraw()); // Contact activity_draw


        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public Note_Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_Category, KEY_DATA, KEY_DATE, KEY_Color, KEY_Draw}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note_Contact noteContact = new Note_Contact(Integer.parseInt(cursor.getString(0))
                , cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6));
        // return categoryContact
        db.close();
        cursor.close();
        return noteContact;
    }

    // Getting All Contacts
    public List<Note_Contact> getAllContacts() {
        List<Note_Contact> noteContactList = new ArrayList<Note_Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note_Contact noteContact = new Note_Contact();
                noteContact.setID(Integer.parseInt(cursor.getString(0)));
                noteContact.setName(cursor.getString(1));
                noteContact.setCategory(cursor.getString(2));
                noteContact.setData(cursor.getString(3));
                noteContact.setDate(cursor.getString(4));
                noteContact.setColor(cursor.getString(5));
                noteContact.setDraw(cursor.getString(6));
                // Adding noteContact to list
                noteContactList.add(noteContact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return noteContactList;
    }

    // Updating single noteContact
    public int updateContact(Note_Contact noteContact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, noteContact.getName());
        values.put(KEY_Category, noteContact.getCategory());
        values.put(KEY_DATA, noteContact.getData());
        values.put(KEY_DATE, noteContact.getDate());
        values.put(KEY_Color, noteContact.getColor());
        values.put(KEY_Draw, noteContact.getDraw());
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(noteContact.getID())});
    }

    // Deleting single Note_Contact
    public void deleteContact(Note_Contact noteContact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(noteContact.getID())});
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