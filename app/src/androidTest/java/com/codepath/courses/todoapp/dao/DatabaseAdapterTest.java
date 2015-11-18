package com.codepath.courses.todoapp.dao;

import android.test.AndroidTestCase;

import com.codepath.courses.todoapp.domain.ToDoItem;

import java.util.List;

public class DatabaseAdapterTest extends AndroidTestCase {
    private static final String FIRST_ITEM = "First Item In test";
    private static final String SECOND_ITEM = "Second Item In test";
    private DatabaseAdapter dba;

    @Override
    protected void setUp() throws Exception {
        DatabaseAdapter.init(getContext());
        dba = DatabaseAdapter.getInstance();
        dba.open();
        dba.deleteAllItems();
    }

    public void testInsert() {
        dba.insertToDoItem(FIRST_ITEM);
        dba.insertToDoItem(SECOND_ITEM);
        List<ToDoItem> items = dba.getAllToDoItems();
        assertTrue(found(items, FIRST_ITEM) == true);
        assertTrue(found(items, SECOND_ITEM) == true);
    }

    private boolean found(List<ToDoItem> items, String title) {
        for (ToDoItem item : items) {
            if (item.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    public void testUpdate() {
        final long _id = dba.insertToDoItem(FIRST_ITEM);
        String modifiedTitle = "Modified First title";
        dba.updateToDoItem(_id, modifiedTitle);
        List<ToDoItem> items = dba.getAllToDoItems();
        assertTrue(found(items, modifiedTitle) == true);
    }

    public void testCheckForEmptyTitle() {
        try {
            dba.insertToDoItem("");
            fail("Should never get here");
        } catch (IllegalArgumentException e) {
            assertEquals("Item must not be empty", e.getMessage());
        }
    }

    public void testDelete() {
        int count = dba.getAllToDoItems().size();
        final long _id = dba.insertToDoItem(FIRST_ITEM);
        List<ToDoItem> toDoItems = dba.getAllToDoItems();
        assertEquals(count + 1, toDoItems.size());

        dba.deleteToDoItem(_id);
        toDoItems = dba.getAllToDoItems();
        assertEquals(count, toDoItems.size());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        dba.deleteAllItems();
        dba.close();
    }
}