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

public class EditItemActivity extends AppCompatActivity {

    public static final String INTENT_ITEM_STRING = EditItemActivity.class.getPackage() + ".item";

    private EditText editItemText;

    private Button editButton;

    private ToDoItemDao toDoItemDao;

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

        toDoItemDao = ToDoItemDao.getInstance();

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

        editItemText = (EditText) findViewById(R.id.editItemText);
        editButton = (Button) findViewById(R.id.editButton);
        editItemText.setText(toDoItem.getTitle());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editItemText.getText().toString().trim();
                System.out.println("Item entered: " + editItemText.getText().toString());
                if (title == null || title.equals("")) {
                    Toast.makeText(EditItemActivity.this, R.string.empty_title, Toast.LENGTH_SHORT).show();
                    return;
                }
                toDoItem.setTitle(title);
                toDoItemDao.update(toDoItem);
                onBackPressed();
            }
        });
    }
}
