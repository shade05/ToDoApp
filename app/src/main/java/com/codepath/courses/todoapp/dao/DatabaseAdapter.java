package com.codepath.courses.todoapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codepath.courses.todoapp.domain.ToDoItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {
    private static DatabaseAdapter databaseAdapter;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public static void init(Context context) {
        databaseAdapter = new DatabaseAdapter(context);
    }

    public static DatabaseAdapter getInstance() {
        return databaseAdapter;
    }

    public DatabaseAdapter open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private Cursor getAllEntries() {
        String[] columns = new String[2];
        columns[0] = "_id";
        columns[1] = "title";
        return database.query("to_do_items", columns, null, null, null, null, null);
    }

    public List<ToDoItem> getAllToDoItems() {
        ArrayList<ToDoItem> items = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if (cursor.moveToFirst()) {
            do {
                System.out.println("id: " + cursor.getInt(0) + ", title: " + cursor.getString(1));
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.set_id(cursor.getLong(0));
                toDoItem.setTitle(cursor.getString(1));
                items.add(toDoItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public boolean exists(String title) {
        Cursor cursor = database.rawQuery(
                "select title from to_do_items where title=?",
                new String[]{title});
        boolean yes = cursor.getCount() >= 1;
        cursor.close();
        return yes;
    }

    public long insertToDoItem(String title) {
        System.out.println("title is : " + title);
        if (title.length() == 0) {
            throw new IllegalArgumentException("Item must not be empty");
        }
        ContentValues values = new ContentValues();
        values.put("title", title);
        return database.insert("to_do_items", null, values);
    }

    public int deleteToDoItem(Long _id) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = _id.toString();
        return database.delete("to_do_items", whereClause, whereArgs);
    }

    public int deleteAllItems() {
        return database.delete("to_do_items", null, null);
    }

    public int updateToDoItem(Long id, String title) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = id.toString();
        ContentValues values = new ContentValues();
        values.put("title", title);
        return database.update("to_do_items", values, whereClause, whereArgs);
    }
}