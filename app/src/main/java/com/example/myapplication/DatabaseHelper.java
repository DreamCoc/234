package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void addToDataBase (ArrayList<ArrayList<String>> getMainData,ArrayList<ArrayList<String[]>> getWordInBracket){
       SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String> form = getMainData.get(1);
        ArrayList<String> name = getMainData.get(0);

        for (int i = 0; i < form.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_1,replaceTextInBrackets(form.get(i)));
            contentValues.put(COLUMN_3,name.get(i));

            ArrayList<String[]> innerList = getWordInBracket.get(i);
            StringBuilder stringBuilder = new StringBuilder();
            for (String[] array : innerList) {
                stringBuilder.append(array[0]).append(", ");
            }
            String result = stringBuilder.toString().trim();
            contentValues.put(COLUMN_2, result);

            db.insert(TABLE_NAME, null, contentValues);
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public static String replaceTextInBrackets(String text) {
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(text);
        StringBuffer stringBuffer = new StringBuffer();

        while (matcher.find()) {
            String bracketContent = matcher.group(1);
            String[] words = bracketContent.split(",");

            if (words.length > 0) {
                String replacement = words[0].trim();
                matcher.appendReplacement(stringBuffer, replacement);
            }
        }
        matcher.appendTail(stringBuffer);

        return stringBuffer.toString();
    }
}