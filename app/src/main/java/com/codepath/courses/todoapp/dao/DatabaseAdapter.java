package com.codepath.courses.todoapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private Cursor getAllEntries() {
        String[] columns = new String[1];
        columns[0] = "title";
        return database.query("to_do_items", columns, null, null, null, null, null);
    }

    public List<String> getAllItems() {
        ArrayList<String> items = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if (cursor.moveToFirst()) {
            do {
                items.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public boolean exists(String name) {
        Cursor cursor = database.rawQuery(
                "select title from to_do_items where title=?",
                new String[]{name});
        boolean yes = cursor.getCount() >= 1;
        cursor.close();
        return yes;
    }

    public long insertItem(String title) {
        if (title.length() == 0) {
            throw new IllegalArgumentException("Item must not be empty");
        }
        ContentValues values = new ContentValues();
        values.put("title", title);
        return database.insert("to_do_items", null, values);
    }

    public int deleteItem(String name) {
        String whereClause = "title = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = name;
        return database.delete("to_do_items", whereClause, whereArgs);
    }

    public int deleteAllItems() {
        return database.delete("to_do_items", null, null);
    }

    public int updateItem(String title) {
        String whereClause = "tile = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = title;
        ContentValues values = new ContentValues();
        values.put("title", title);
        return database.update("to_do_items", values, whereClause, whereArgs);
    }
}