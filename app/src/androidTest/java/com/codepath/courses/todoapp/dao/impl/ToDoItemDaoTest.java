package com.codepath.courses.todoapp.dao.impl;

import android.test.AndroidTestCase;

import com.codepath.courses.todoapp.domain.ToDoItem;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by deepaks on 11/17/15.
 */
public class ToDoItemDaoTest extends AndroidTestCase {

    private ToDoItemDao toDoItemDao;

    @Override
    protected void setUp() throws Exception {
        ToDoItemDao.init(getContext());
        toDoItemDao = ToDoItemDao.getInstance();
    }

    public void testCrud() {
        Long id = insert();
        ToDoItem insertedToDoItem = get(id);
        System.out.println("INsertd item: " + insertedToDoItem);
        update(insertedToDoItem);
        delete(insertedToDoItem);
    }

    private Long insert() {
        ToDoItem toDoItem = createToDoItem();
        Long id = toDoItemDao.save(toDoItem);
        System.out.println("Id generated: " + id);
        return id;
    }

    private ToDoItem get(Long id) {
        return toDoItemDao.get(id);
    }

    private void update(ToDoItem toDoItem) {
        String updatedTitle = "Updated Title" + System.currentTimeMillis();
        toDoItem.setTitle(updatedTitle);
        toDoItemDao.update(toDoItem);
        ToDoItem updatedToDoItem = get(toDoItem.get_id());
        Assert.assertTrue(updatedToDoItem.getTitle().equals(updatedTitle));
    }

    private void delete(ToDoItem toDoItem) {
        toDoItemDao.delete(toDoItem.get_id());
        List<ToDoItem> toDoItems = toDoItemDao.getAllToDoItems();
        Assert.assertFalse(found(toDoItems, toDoItem.get_id()));
    }

    public void testGetAll() {
        final List<ToDoItem> toDoItems = toDoItemDao.getAllToDoItems();
        System.out.println("All items: " + toDoItems);
    }


    private ToDoItem createToDoItem() {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setTitle("Test Title : " + System.currentTimeMillis());
        toDoItem.setDateTime(System.currentTimeMillis() + "");
        toDoItem.setStatus("TODO");

        return toDoItem;
    }

    private boolean found(List<ToDoItem> toDoItems, Long id) {
        for (ToDoItem toDoItem : toDoItems) {
            if (toDoItem.get_id().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
