package com.codepath.courses.todoapp;

import android.app.Application;

import com.codepath.courses.todoapp.di.component.AppComponent;
import com.codepath.courses.todoapp.di.component.DaggerAppComponent;
import com.codepath.courses.todoapp.di.module.AppModule;
import com.codepath.courses.todoapp.di.module.DaoModule;

/**
 * Created by deepaks on 11/12/15.
 */
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;
    private AppComponent mAppComponent;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //ToDoItemDao.init(getApplicationContext());
        //mInstance = this;

        mAppComponent = DaggerAppComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this))
                .daoModule(new DaoModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}