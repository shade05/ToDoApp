package com.codepath.courses.todoapp;

import android.app.Application;

import com.codepath.courses.todoapp.dao.DatabaseAdapter;

/**
 * Created by deepaks on 11/12/15.
 */
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseAdapter.init(getApplicationContext());
        mInstance = this;
    }
}