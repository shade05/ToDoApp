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

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddToDoItemActivity extends AppCompatActivity {

    @Bind(R.id.editAddTitle)
    protected EditText editTitle;

    @Bind(R.id.editDescription)
    protected EditText editDescription;

    @Bind(R.id.editDueDate)
    protected EditText editDueDate;

    @Bind(R.id.editStatus)
    protected EditText editStatus;

    @Bind(R.id.addToDoItemButton)
    protected Button addButton;

    @Inject
    ToDoItemDao toDoItemDao;

    public static Intent getIntent(Context context) {
        final Intent intent = new Intent(context, AddToDoItemActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do_item);

        ((AppController) getApplication()).getAppComponent().inject(this);
        //toDoItemDao = ToDoItemDao.getInstance();

        ButterKnife.bind(this);

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
            editText.setError(getApplicationContext().getString(errorCode));
            Toast.makeText(AddToDoItemActivity.this, errorCode, Toast.LENGTH_SHORT).show();
            return null;
        } else {
            editText.setError(null);
        }

        return value;
    }

}
