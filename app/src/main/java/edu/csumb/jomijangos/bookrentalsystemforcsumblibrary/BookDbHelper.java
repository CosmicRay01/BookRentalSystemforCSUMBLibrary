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

public class BookDbHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "books.db";

    // Table Names
    private static final String TABLE_BOOKS = "books";

    // Common Columns names
    private static final String KEY_ID = "id";

    // TRANSACTION Table - column names
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "pickup";
    private static final String KEY_ISBN = "ISBN";
    private static final String KEY_FEE = "fee";


    // transaction table create statement
    private static final String CREATE_BOOK_TABLE = "CREATE TABLE "
            + TABLE_BOOKS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TITLE + " TEXT," + KEY_AUTHOR + " TEXT,"
            + KEY_ISBN + " TEXT," + KEY_FEE + " DOUBLE"
            + ")";

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new transaction
    void addBook(Book transaction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, transaction.getTitle()); // Contact Phone
        values.put(KEY_AUTHOR, transaction.getAuthor()); // Contact Name
        values.put(KEY_ISBN, transaction.getISBN()); // Contact Phone
        values.put(KEY_FEE, transaction.getFee()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_BOOKS, null, values);
        db.close(); // Closing database connection
    }

    public List<Book> getAllBooks() {
        List<Book> tags = new ArrayList<Book>();
        String selectQuery = "SELECT  * FROM " + TABLE_BOOKS;

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Book t = new Book();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                t.setAuthor(c.getString(c.getColumnIndex(KEY_AUTHOR)));
                t.setISBN(c.getString(c.getColumnIndex(KEY_ISBN)));
                t.setFee(c.getDouble(c.getColumnIndex(KEY_FEE)));

                // adding to tags list
                tags.add(t);
            } while (c.moveToNext());
        }
        return tags;
    }
}