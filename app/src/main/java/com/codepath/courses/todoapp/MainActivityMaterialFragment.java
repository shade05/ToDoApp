package com.codepath.courses.todoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.courses.todoapp.adapter.ToDoItemAdapter;
import com.codepath.courses.todoapp.dao.impl.ToDoItemDao;
import com.codepath.courses.todoapp.dialog.MyAlertDialogFragment;
import com.codepath.courses.todoapp.domain.ToDoItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view
 */
public class MainActivityMaterialFragment extends Fragment {

    public static final int MAIN_ACTIVITY_FRAGMENT = 1;
    private final static int SINGLE_DELETE = 0;
    private final static String TAG = MainActivityMaterialFragment.class.getSimpleName();
    @Bind(R.id.recyleView)
    protected RecyclerView mRecyclerView;
    @Inject
    ToDoItemDao toDoItemDao;
    private List<ToDoItem> items;
    private ToDoItemAdapter mToDoItemAdapter;


    public MainActivityMaterialFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppController) getActivity().getApplication()).getAppComponent().inject(this);

        final View rootView = inflater.inflate(R.layout.fragment_material_main, container, false);

        ButterKnife.bind(this, rootView);

        items = populateItems();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mToDoItemAdapter = new ToDoItemAdapter(items, R.layout.recyleview_todoitem, getActivity(), this);
        mRecyclerView.setAdapter(mToDoItemAdapter);
        mToDoItemAdapter.setOnItemClickListener(new ToDoItemAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(TAG, "onItemClick position: " + position);
                getContext().startActivity(EditItemActivity.getIntent(getContext(), items.get(position)));
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d(TAG, "onItemLongClick pos = " + position);
                show(position);
            }
        });


        return rootView;
    }

    public void removeToDoItem(int position) {
        final ToDoItem removedItem = items.remove(position);
        toDoItemDao.delete(removedItem.get_id());
        mToDoItemAdapter.notifyDataSetChanged();
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
        mToDoItemAdapter = new ToDoItemAdapter(items, R.layout.recyleview_todoitem, getActivity(), this);
        mRecyclerView.setAdapter(mToDoItemAdapter);
    }
}
