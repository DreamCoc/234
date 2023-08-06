package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final String TABLE_NAME = "mytable";
    private static final String COLUMN_1 = "column_1";
    private static final String COLUMN_2 = "column_2";
    private static final String COLUMN_3 = "column_3";
    private static final String COLUMN_4 = "column_4";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_1 + " TEXT, " +
                COLUMN_2 + " TEXT," +
                COLUMN_3 + " TEXT,"+
                COLUMN_4 + " TEXT"+
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String column1Value, String[] column2Value,String column3Value,String[][] column4Value ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1, column1Value);
        contentValues.put(COLUMN_2, Arrays.toString(column2Value));
        contentValues.put(COLUMN_3, column3Value);
        contentValues.put(COLUMN_4, Arrays.toString(column4Value));
        db.insert(TABLE_NAME, null, contentValues);

    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

}