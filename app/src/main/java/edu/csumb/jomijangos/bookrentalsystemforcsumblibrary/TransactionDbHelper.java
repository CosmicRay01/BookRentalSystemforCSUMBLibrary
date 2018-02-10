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

public class TransactionDbHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "transaction.db";

    // Table Names
    private static final String TABLE_TRANSACTIONS = "transactions";

    // Common Columns names
    private static final String KEY_ID = "id";

    // TRANSACTION Table - column names
    private static final String KEY_TYPE = "type";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_DATE = "date";

    // transaction table create statement
    private static final String CREATE_TRANSACTION_TABLE = "CREATE TABLE "
            + TABLE_TRANSACTIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TYPE + " TEXT," + KEY_USERNAME + " TEXT,"
            + KEY_DATE + " TEXT"
            + ")";

    public TransactionDbHelper(Context context) {
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new transaction
    void addTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, transaction.getType()); // Contact Phone
        values.put(KEY_USERNAME, transaction.getUsername()); // Contact Name
        values.put(KEY_DATE, transaction.getDate()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_TRANSACTIONS, null, values);
        db.close(); // Closing database connection
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> tags = new ArrayList<Transaction>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTIONS;

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                if(!c.getString(c.getColumnIndex(KEY_USERNAME)).equals("username")) {
                    Transaction t = new Transaction();
                    t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                    t.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
                    t.setUsername(c.getString(c.getColumnIndex(KEY_USERNAME)));
                    t.setDate(c.getString(c.getColumnIndex(KEY_DATE)));

                    // adding to tags list
                    tags.add(t);
                }
            } while (c.moveToNext());
        }
        return tags;
    }

    public List<Transaction> getAllTransactions(String username) {
        List<Transaction> tags = new ArrayList<Transaction>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTIONS;

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                if(c.getString(c.getColumnIndex(KEY_USERNAME)).equals(username)) {
                    Transaction t = new Transaction();
                    t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                    t.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
                    t.setUsername(c.getString(c.getColumnIndex(KEY_USERNAME)));
                    t.setDate(c.getString(c.getColumnIndex(KEY_DATE)));

                    // adding to tags list
                    tags.add(t);
                }
            } while (c.moveToNext());
        }
        return tags;
    }
}