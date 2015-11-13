package com.codepath.courses.todoapp.domain;

import java.io.Serializable;

/**
 * Created by deepaks on 11/12/15.
 */
public class ToDoItem implements Serializable {

    private Integer id;

    private String title;


    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ToDoItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
