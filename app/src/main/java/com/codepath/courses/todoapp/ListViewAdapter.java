package com.codepath.courses.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codepath.courses.todoapp.domain.ToDoItem;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private List<ToDoItem> toDoItems;
    private Context context;

    public ListViewAdapter(List<ToDoItem> toDoItems, Context context) {
        this.toDoItems = toDoItems;
        this.context = context;
    }

    public List<ToDoItem> getToDoItems() {
        return toDoItems;
    }

    public void setToDoItems(final List<ToDoItem> toDoItems) {
        this.toDoItems = toDoItems;
    }

    @Override
    public int getCount() {
        return toDoItems.size();
    }

    @Override
    public Object getItem(int position) {
        return toDoItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return toDoItems.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup arg2) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, arg2, false);
        }

        TextView chapterName = (TextView) view.findViewById(R.id.textView1);

        ToDoItem toToItem = toDoItems.get(position);

        chapterName.setText(toToItem.getTitle());

        return view;
    }

}