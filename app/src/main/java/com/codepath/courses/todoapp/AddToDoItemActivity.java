package com.codepath.courses.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.courses.todoapp.dao.impl.ToDoItemDao;
import com.codepath.courses.todoapp.domain.ToDoItem;

public class AddToDoItemActivity extends AppCompatActivity {

    private EditText editTitle;

    private EditText editDescription;

    private EditText editDueDate;

    private EditText editStatus;

    private Button addButton;

    private ToDoItemDao toDoItemDao;

    public static Intent getIntent(Context context) {
        final Intent intent = new Intent(context, AddToDoItemActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do_item);


        toDoItemDao = ToDoItemDao.getInstance();

        editTitle = (EditText) findViewById(R.id.editAddTitle);
        editDescription = (EditText) findViewById(R.id.editDescription);
        editDueDate = (EditText) findViewById(R.id.editDueDate);
        editStatus = (EditText) findViewById(R.id.editStatus);
        addButton = (Button) findViewById(R.id.addToDoItemButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoItem toDoItem = createToDoItem();
                if (toDoItem == null) {
                    return;
                }

                toDoItemDao.save(toDoItem);
                onBackPressed();
            }
        });
    }

    private ToDoItem createToDoItem() {
        ToDoItem toDoItem = new ToDoItem();
        String value = null;
        value = checkEditText(editTitle, R.string.empty_title);
        if (value == null) {
            return null;
        }
        toDoItem.setTitle(value);
        value = checkEditText(editDescription, R.string.empty_description);
        if (value == null) {
            return null;
        }
        toDoItem.setDescription(value);
        value = checkEditText(editDueDate, R.string.empty_due_date);
        if (value == null) {
            return null;
        }
        toDoItem.setDateTime(value);
        value = checkEditText(editStatus, R.string.empty_status);
        if (value == null) {
            return null;
        }

        toDoItem.setStatus(value);

        return toDoItem;
    }

    private String checkEditText(EditText editText, int errorCode) {
        String value = editText.getText().toString().trim();

        if (value == null || value.equals("")) {
            Toast.makeText(AddToDoItemActivity.this, errorCode, Toast.LENGTH_SHORT).show();
            return null;
        }

        return value;
    }

}
