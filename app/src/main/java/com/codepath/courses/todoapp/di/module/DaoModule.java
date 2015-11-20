package com.codepath.courses.todoapp.di.module;

import android.app.Application;

import com.codepath.courses.todoapp.dao.impl.ToDoItemDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by deepaks on 11/19/15.
 */
@Module
public class DaoModule {

    @Provides
    @Singleton
    ToDoItemDao providesToDoItemDao(Application application) {
        ToDoItemDao.init(application.getApplicationContext());
        return ToDoItemDao.getInstance();
    }
}
