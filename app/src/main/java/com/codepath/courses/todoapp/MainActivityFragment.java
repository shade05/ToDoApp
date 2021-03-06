package com.codepath.courses.todoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.courses.todoapp.dao.impl.ToDoItemDao;
import com.codepath.courses.todoapp.dialog.MyAlertDialogFragment;
import com.codepath.courses.todoapp.domain.ToDoItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static final int MAIN_ACTIVITY_FRAGMENT = 1;
    private final static int SINGLE_DELETE = 0;
    @Bind(R.id.listView)
    protected ListView listView;
    @Inject
    ToDoItemDao toDoItemDao;
    private List<ToDoItem> items;
    private ListViewAdapter itemsAdapter;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppController) getActivity().getApplication()).getAppComponent().inject(this);

        //toDoItemDao = ToDoItemDao.getInstance();
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

        items = populateItems();
        itemsAdapter = new ListViewAdapter(items, getActivity());
        listView.setAdapter(itemsAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int position, final long l) {
                show(position);
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

    public void removeToDoItem(int position) {
        final ToDoItem removedItem = items.remove(position);
        itemsAdapter.notifyDataSetChanged();
        toDoItemDao.delete(removedItem.get_id());
    }

    private void show(int position) {
        AppCompatDialogFragment dialogFragment = MyAlertDialogFragment.newInstance(SINGLE_DELETE, position);
        dialogFragment.setTargetFragment(this, MAIN_ACTIVITY_FRAGMENT);
        dialogFragment.show(getFragmentManager(), null);
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
