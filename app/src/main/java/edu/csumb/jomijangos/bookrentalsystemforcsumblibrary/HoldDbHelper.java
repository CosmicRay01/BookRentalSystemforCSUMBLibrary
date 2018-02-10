package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

public class HoldDbHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "holds.db";

    // Table Names
    private static final String TABLE_HOLDS = "holds";

    // Common Columns names
    private static final String KEY_ID = "id";

    // TRANSACTION Table - column names
    private static final String KEY_TITLE = "title";
    private static final String KEY_USER = "user";
    private static final String KEY_PICKUP = "pickup";
    private static final String KEY_RETURN = "return";

    // transaction table create statement
    private static final String CREATE_TRANSACTION_TABLE = "CREATE TABLE "
            + TABLE_HOLDS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TITLE + " TEXT," + KEY_USER + " TEXT,"
            + KEY_PICKUP + " TEXT," + KEY_RETURN + " TEXT"
            + ")";

    public HoldDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOLDS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new transaction
    void addHold(Hold transaction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, transaction.getBookTitle()); // Contact Phone
        values.put(KEY_USER, transaction.getUsername()); // Contact Name
        values.put(KEY_PICKUP, transaction.getPickUpDate()); // Contact Name
        values.put(KEY_RETURN, transaction.getReturnDate()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_HOLDS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single hold
    Hold getHold(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HOLDS, new String[] { KEY_ID,
                        KEY_TITLE, KEY_USER, KEY_PICKUP, KEY_RETURN }, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Hold contact = new Hold(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4));
        // return contact
        return contact;
    }



    public List<Hold> getAllCompleteHolds() {
        List<Hold> tags = new ArrayList<Hold>();
        String selectQuery = "SELECT  * FROM " + TABLE_HOLDS;

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                if(!c.getString(c.getColumnIndex(KEY_USER)).equals("username")) {
                    Hold t = new Hold();
                    t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                    t.setBookTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                    t.setUsername(c.getString(c.getColumnIndex(KEY_USER)));
                    t.setPickUpDate(c.getString(c.getColumnIndex(KEY_PICKUP)));
                    t.setReturnDate(c.getString(c.getColumnIndex(KEY_RETURN)));

                    // adding to tags list
                    tags.add(t);
                }
            } while (c.moveToNext());
        }
        return tags;
    }

    public List<Hold> getAllHolds() {
        List<Hold> tags = new ArrayList<Hold>();
        String selectQuery = "SELECT  * FROM " + TABLE_HOLDS;

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                    Hold t = new Hold();
                    t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                    t.setBookTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                    t.setUsername(c.getString(c.getColumnIndex(KEY_USER)));
                    t.setPickUpDate(c.getString(c.getColumnIndex(KEY_PICKUP)));
                    t.setReturnDate(c.getString(c.getColumnIndex(KEY_RETURN)));

                    // adding to tags list
                    tags.add(t);
            } while (c.moveToNext());
        }
        return tags;
    }

    public List<Hold> getAllHolds(String username) {
        List<Hold> tags = new ArrayList<Hold>();
        String selectQuery = "SELECT  * FROM " + TABLE_HOLDS;

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                if(c.getString(c.getColumnIndex(KEY_USER)).equals(username)) {
                    Hold t = new Hold();
                    t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                    t.setBookTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                    t.setUsername(c.getString(c.getColumnIndex(KEY_USER)));
                    t.setPickUpDate(c.getString(c.getColumnIndex(KEY_PICKUP)));
                    t.setReturnDate(c.getString(c.getColumnIndex(KEY_RETURN)));

                    // adding to tags list
                    tags.add(t);
                }
            } while (c.moveToNext());
        }
        return tags;
    }

    // Updating single contact
    public int updateContact(Hold contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, contact.getBookTitle());
        values.put(KEY_USER, contact.getUsername());
        values.put(KEY_PICKUP, contact.getPickUpDate());
        values.put(KEY_RETURN, contact.getReturnDate());

        // updating row
        return db.update(TABLE_HOLDS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_HOLDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public void deleteById(String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HOLDS, KEY_ID + " = ?",
                new String[] { contact });
        db.close();
    }

    // Getting single account
    Hold getHold(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HOLDS, new String[] { KEY_ID,
                        KEY_TITLE, KEY_USER, KEY_PICKUP, KEY_RETURN }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Hold contact = new Hold(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        return contact;
    }
}