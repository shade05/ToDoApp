package com.codepath.courses.todoapp.domain;

import java.io.Serializable;

/**
 * Created by deepaks on 11/12/15.
 */
public class ToDoItem implements Serializable {

    private Long _id;

    private String title;

    private String description;

    private String status;

    private String dateTime;

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long id) {
        this._id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(final String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ToDoItem{" +
                "dateTime='" + dateTime + '\'' +
                ", id=" + _id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
