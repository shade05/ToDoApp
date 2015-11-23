package com.codepath.courses.todoapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.courses.todoapp.R;
import com.codepath.courses.todoapp.dialog.MyAlertDialogFragment;
import com.codepath.courses.todoapp.domain.ToDoItem;

import java.util.List;

public class ToDoItemAdapter extends RecyclerView.Adapter<ToDoItemAdapter.ViewHolder> {

    public static final int MAIN_ACTIVITY_FRAGMENT = 1;
    private final static int SINGLE_DELETE = 0;

    private static ClickListener clickListener;

    private final List<ToDoItem> mToDoItems;
    private final int mRowLayout;
    private final Context mContext;
    private final Fragment mFragment;

    public ToDoItemAdapter(List<ToDoItem> toDoItems, int rowLayout, Context context, Fragment fragment) {
        mToDoItems = toDoItems;
        mContext = context;
        mRowLayout = rowLayout;
        mFragment = fragment;
    }

    // Create a new ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(mContext).inflate(mRowLayout, viewGroup, false);
        return new ToDoItemAdapter.ViewHolder(v);
    }

    // Set up displayable information
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mToDoItem = mToDoItems.get(position);
        viewHolder.titleTextView.setText(viewHolder.mToDoItem.getTitle());
        viewHolder.statusTextView.setText(viewHolder.mToDoItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return (null == mToDoItems) ? 0 : mToDoItems.size();
    }

    public void add(ToDoItem listItem) {
        mToDoItems.add(listItem);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ClickListener sclickListener) {
        clickListener = sclickListener;
    }

    public void removeAllPlaceBadges() {
        mToDoItems.clear();
        this.notifyDataSetChanged();
    }

    public void show(int position) {
        AppCompatDialogFragment dialogFragment = MyAlertDialogFragment.newInstance(SINGLE_DELETE, position);
        dialogFragment.setTargetFragment(mFragment, MAIN_ACTIVITY_FRAGMENT);
        dialogFragment.show(mFragment.getFragmentManager(), null);
    }


    // ViewHolder for RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final TextView titleTextView;
        public final TextView statusTextView;
        private ToDoItem mToDoItem;

        public ViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.titleTextViewRV);
            statusTextView = (TextView) view.findViewById(R.id.statusTextViewRV);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(final View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }

    // ViewHolder for RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final TextView titleTextView;
        public final TextView statusTextView;
        private ToDoItem mToDoItem;

        public ViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.titleTextViewRV);
            statusTextView = (TextView) view.findViewById(R.id.statusTextViewRV);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(final View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }
}