package com.codepath.courses.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.courses.todoapp.dao.impl.ToDoItemDao;
import com.codepath.courses.todoapp.domain.ToDoItem;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditItemActivity extends AppCompatActivity {

    public static final String INTENT_ITEM_STRING = EditItemActivity.class.getPackage() + ".item";

    @Bind(R.id.editUpdateTitle)
    protected EditText editTitle;

    @Bind(R.id.editUpdateDescription)
    protected EditText editDescription;

    @Bind(R.id.editUpdateDueDate)
    protected EditText editDueDate;

    @Bind(R.id.editUpdateStatus)
    protected EditText editStatus;

    @Bind(R.id.editButton)
    protected Button editButton;

    @Inject
    ToDoItemDao toDoItemDao;

    private ToDoItem toDoItem;

    public static Intent getIntent(Context context, ToDoItem toDoItem) {
        if (toDoItem == null || TextUtils.isEmpty(toDoItem.getTitle())) {
            throw new IllegalArgumentException("ToDoItem cannot be null or empty");
        }
        final Intent intent = new Intent(context, EditItemActivity.class);
        intent.putExtra(EditItemActivity.INTENT_ITEM_STRING, toDoItem);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppController) getApplication()).getAppComponent().inject(this);

        //toDoItemDao = ToDoItemDao.getInstance();

        setContentView(R.layout.activity_edit_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.edit_item_title);
        }

        toDoItem = (ToDoItem) getIntent().getSerializableExtra(INTENT_ITEM_STRING);
        System.out.println("item value in onCreate is " + toDoItem);
        if (toDoItem == null || TextUtils.isEmpty(toDoItem.getTitle())) {
            throw new IllegalStateException("Required item value not found");
        }

        ButterKnife.bind(this);

        updateUI(toDoItem);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean updated = updateToDoItem();
                if (!updated) {
                    return;
                }
                toDoItemDao.update(toDoItem);
                onBackPressed();
            }
        });
    }

    private boolean updateToDoItem() {

        String value = null;
        value = checkEditText(editTitle, R.string.empty_title);
        if (value == null) {
            return false;
        }
        toDoItem.setTitle(value);
        value = checkEditText(editDescription, R.string.empty_description);
        if (value == null) {
            return false;
        }
        toDoItem.setDescription(value);
        value = checkEditText(editDueDate, R.string.empty_due_date);
        if (value == null) {
            return false;
        }
        toDoItem.setDateTime(value);
        value = checkEditText(editStatus, R.string.empty_status);
        if (value == null) {
            return false;
        }

        toDoItem.setStatus(value);

        return true;
    }

    private void updateUI(ToDoItem toDoItem) {
        editTitle.setText(toDoItem.getTitle());
        editDescription.setText(toDoItem.getDescription());
        editDueDate.setText(toDoItem.getDateTime());
        editStatus.setText(toDoItem.getStatus());
    }

    private String checkEditText(EditText editText, int errorCode) {
        String value = editText.getText().toString().trim();

        if (value == null || value.equals("")) {
            Toast.makeText(EditItemActivity.this, errorCode, Toast.LENGTH_SHORT).show();
            return null;
        }

        return value;
    }
}
