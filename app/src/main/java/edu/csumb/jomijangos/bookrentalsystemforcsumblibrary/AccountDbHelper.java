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

public class AccountDbHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "account.db";

    // Table Names
    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String TABLE_TRANSACTIONS = "transactions";

    // Common Columns names
    private static final String KEY_ID = "id";

    // ACCOUNT Table - column names
    private static final String KEY_USER = "name";
    private static final String KEY_PASS = "phone_number";

    // TRANSACTION Table - column names
    private static final String KEY_TYPE = "type";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_DATE = "date";

    // account table create statement
    public static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE "
            + TABLE_ACCOUNTS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_USER + " TEXT," + KEY_PASS + " TEXT"
            + ")";

    // transaction table create statement
    private static final String CREATE_TRANSACTION_TABLE = "CREATE TABLE "
            + TABLE_TRANSACTIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TYPE + " TEXT," + KEY_USERNAME + " TEXT,"
            + KEY_DATE + " TEXT"
            + ")";

    public AccountDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER, account.getUsername()); // Contact Name
        values.put(KEY_PASS, account.getPassword()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_ACCOUNTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single account
    Account getAccount(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[] { KEY_ID,
                        KEY_USER, KEY_PASS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Account contact = new Account(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<Account> getAllAccounts() {
        List<Account> contactList = new ArrayList<Account>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Account contact = new Account();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setUsername(cursor.getString(1));
                contact.setPassword(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(Account contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER, contact.getUsername());
        values.put(KEY_PASS, contact.getPassword());

        // updating row
        return db.update(TABLE_ACCOUNTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
    }

    // Deleting single contact
    public void deleteContact(Account contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCOUNTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}