package com.codepath.courses.todoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.codepath.courses.todoapp.MainActivityFragment;
import com.codepath.courses.todoapp.R;

public class MyAlertDialogFragment extends AppCompatDialogFragment {
    public static final String OP_ID = "ID";
    public static final String POSITION = "POSITION";

    public static MyAlertDialogFragment newInstance(int dialogID, int poistion) {
        MyAlertDialogFragment dialogFragment = new MyAlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(OP_ID, dialogID);
        bundle.putInt(POSITION, poistion);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Inflate the dialog
        LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialogView = null;

        switch (getArguments().getInt(OP_ID)) {

            case 0: {
                dialogView = layoutInflater.inflate(R.layout.delete_confirm_layout, null);

                // Attach listener to "No" Button
                Button noButton = (Button) dialogView
                        .findViewById(R.id.no_button);
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });

                // Attach listener to "Yes" Button
                Button yesButton = (Button) dialogView
                        .findViewById(R.id.yes_button);
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivityFragment) getTargetFragment()).removeToDoItem(getArguments().getInt(POSITION));
                        dismiss();
                    }
                });

                break;
            }
        }

        if (null != dialogView) {

            // Create the AlertDialog
            return new AlertDialog.Builder(getActivity()).setView(dialogView)
                    .create();
        } else {
            return super.onCreateDialog(savedInstanceState);
        }
    }
}