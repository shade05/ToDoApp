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

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private List<String> items;

    private ArrayAdapter<String> itemsAdapter;

    private EditText editText;

    private ListView listView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        items = new ArrayList<>();
        items.add("First Item");
        items.add("Second Item");
        itemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        editText = (EditText) rootView.findViewById(R.id.editText);

        rootView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsAdapter.add(editText.getText().toString());
                editText.setText("");
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
        return rootView;
    }
}
