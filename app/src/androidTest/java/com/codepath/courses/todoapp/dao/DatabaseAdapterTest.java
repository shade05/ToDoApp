package com.codepath.courses.todoapp.dao;

import android.test.AndroidTestCase;

import java.util.List;

public class DatabaseAdapterTest extends AndroidTestCase {
    private static final String FIRST_ITEM = "First Item";
    private static final String SECOND_ITEM = "Second Item";
    private DatabaseAdapter dba;

    @Override
    protected void setUp() throws Exception {
        dba = new DatabaseAdapter(getContext());
        dba.open();
        dba.deleteAllItems();
    }

    public void testInsertAndGetAllItems() {
        dba.insertItem(FIRST_ITEM);
        dba.insertItem(SECOND_ITEM);
        List<String> names = dba.getAllItems();
        assertTrue(names.contains(FIRST_ITEM));
        assertTrue(names.contains(SECOND_ITEM));
    }

    public void testExceptionForEmptyName() {
        try {
            dba.insertItem("");
            fail("Should never get here");
        } catch (IllegalArgumentException e) {
            assertEquals("Item must not be empty", e.getMessage());
        }
    }

    public void testDeleteName() {
        int count = dba.getAllItems().size();
        dba.insertItem(FIRST_ITEM);
        assertEquals(count + 1, dba.getAllItems().size());

        dba.deleteItem(FIRST_ITEM);
        assertEquals(count, dba.getAllItems().size());
    }

    public void testExists() {
        assertFalse(dba.getAllItems().contains(FIRST_ITEM));

        dba.insertItem(FIRST_ITEM);
        assertTrue(dba.getAllItems().contains(FIRST_ITEM));

        dba.deleteItem(FIRST_ITEM);
        assertFalse(dba.getAllItems().contains(FIRST_ITEM));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        dba.deleteAllItems();
        dba.close();
    }
}