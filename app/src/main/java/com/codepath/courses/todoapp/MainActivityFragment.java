package com.codepath.courses.todoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.courses.todoapp.dao.impl.ToDoItemDao;
import com.codepath.courses.todoapp.domain.ToDoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ToDoItemDao toDoItemDao;
    private List<ToDoItem> items;
    private ListViewAdapter itemsAdapter;
    private EditText editText;
    private ListView listView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toDoItemDao = ToDoItemDao.getInstance();
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        items = populateItems();
        itemsAdapter = new ListViewAdapter(items, getActivity());
        listView.setAdapter(itemsAdapter);
        editText = (EditText) rootView.findViewById(R.id.editText);

        rootView.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editText.getText().toString().trim();
                if (title == null || title.equals("")) {
                    Toast.makeText(getActivity(), R.string.empty_title, Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println("Item entered: " + title);
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.setTitle(editText.getText().toString());
                long _id = toDoItemDao.save(toDoItem);
                toDoItem.set_id(_id);
                toDoItem.setTitle(editText.getText().toString());
                itemsAdapter.getToDoItems().add(toDoItem);
                itemsAdapter.notifyDataSetChanged();
                editText.setText("");
                System.out.println("Items in array: " + items);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                final ToDoItem removedItem = items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                toDoItemDao.delete(removedItem.get_id());
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
        items = toDoItemDao.getAllToDoItems();
        System.out.println("items retrieved: " + items);
        if (items == null)
            items = new ArrayList<>();
        return items;
    }

    @Override
    public void onResume() {
        super.onResume();
        items = populateItems();
        itemsAdapter = new ListViewAdapter(items, getActivity());
        listView.setAdapter(itemsAdapter);
    }
}
