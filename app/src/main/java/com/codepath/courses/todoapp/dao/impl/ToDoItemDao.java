package com.codepath.courses.todoapp.dao.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.codepath.courses.todoapp.dao.DatabaseHelper;
import com.codepath.courses.todoapp.dao.GenericDAO;
import com.codepath.courses.todoapp.domain.ToDoItem;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by deepaks on 11/17/15.
 */
public class ToDoItemDao implements GenericDAO<ToDoItem> {

    private static ToDoItemDao INSTANCE;
    private SQLiteDatabase db;

    private ToDoItemDao(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 2);
        db = dbHelper.getWritableDatabase();
    }

    public static void init(Context context) {
        INSTANCE = new ToDoItemDao(context);
    }

    public static ToDoItemDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Long save(final ToDoItem toDoItem) {
        final long id = cupboard().withDatabase(db).put(toDoItem);
        return id;
    }

    @Override
    public ToDoItem get(final Long id) {
        return cupboard().withDatabase(db).get(ToDoItem.class, id);
    }

    @Override
    public void update(final ToDoItem toDoItem) {
        cupboard().withDatabase(db).put(toDoItem);
    }

    @Override
    public void delete(final Long id) {
        cupboard().withDatabase(db).delete(ToDoItem.class, id);
    }

    public List<ToDoItem> getAllToDoItems() {
        final List<ToDoItem> toDoItems = new ArrayList<>();
        final QueryResultIterable<ToDoItem> iterator = cupboard().withDatabase(db).query(ToDoItem.class).query();

        for (ToDoItem toDoItem : iterator) {
            toDoItems.add(toDoItem);
        }
        iterator.close();

        return toDoItems;
    }
}
