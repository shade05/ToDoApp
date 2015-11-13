package com.codepath.courses.todoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.courses.todoapp.dao.DatabaseAdapter;
import com.codepath.courses.todoapp.domain.ToDoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private DatabaseAdapter databaseAdapter;
    private List<ToDoItem> items;
    private ArrayAdapter<ToDoItem> itemsAdapter;
    private EditText editText;
    private ListView listView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseAdapter = DatabaseAdapter.getInstance();
        databaseAdapter.open();
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        items = populateItems();
        itemsAdapter = new ArrayAdapter<ToDoItem>(getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        editText = (EditText) rootView.findViewById(R.id.editText);

        rootView.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Item entered: " + editText.getText().toString());
                long _id = databaseAdapter.insertToDoItem(editText.getText().toString());
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.setId((int) _id);
                toDoItem.setTitle(editText.getText().toString());
                itemsAdapter.add(toDoItem);
                editText.setText("");
                System.out.println("Items in array: " + items);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                final ToDoItem removedItem = items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                databaseAdapter.deleteToDoItem(removedItem.getId());
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long l) {
                System.out.println("Postion clicked is : " + position);
                final ToDoItem toDoItem = (ToDoItem) listView.getItemAtPosition(position);
                startActivity(EditItemActivity.getIntent(getActivity(), toDoItem));
            }
        });
        return rootView;
    }

    private List<ToDoItem> populateItems() {
        List<ToDoItem> items = null;
        items = databaseAdapter.getAllToDoItems();
        System.out.println("items retrieved: " + items);
        if (items == null)
            items = new ArrayList<>();
        return items;
    }
}
