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

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    DatabaseAdapter databaseAdapter;
    private List<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private EditText editText;
    private ListView listView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseAdapter = new DatabaseAdapter(getActivity());
        databaseAdapter.open();
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        items = populateItems();
        itemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        editText = (EditText) rootView.findViewById(R.id.editText);

        rootView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Item entered: " + editText.getText().toString());
                itemsAdapter.add(editText.getText().toString());
                editText.setText("");
                System.out.println("Items in array: " + items);
                updateDatabase(items);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                updateDatabase(items);
                return true;
            }
        });
        return rootView;
    }

    private List<String> populateItems() {
        List<String> items = null;
        items = databaseAdapter.getAllItems();
        if (items == null)
            items = new ArrayList<>();
        return items;
    }

    private void updateDatabase(List<String> items) {
        System.out.println("Items in array: " + items);
        if (items != null && items.size() > 0) {
            for (String item : items) {
                System.out.println("Item  " + item);
                if (item != null && !item.equals("")) {
                    databaseAdapter.insertItem(item);
                }
            }
        }
    }
}
