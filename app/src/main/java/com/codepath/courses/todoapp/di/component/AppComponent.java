package com.codepath.courses.todoapp.di.component;

import com.codepath.courses.todoapp.AddToDoItemActivity;
import com.codepath.courses.todoapp.EditItemActivity;
import com.codepath.courses.todoapp.MainActivityFragment;
import com.codepath.courses.todoapp.MainActivityMaterialFragment;
import com.codepath.courses.todoapp.di.module.AppModule;
import com.codepath.courses.todoapp.di.module.DaoModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by deepaks on 11/19/15.
 */
@Singleton
@Component(modules = {AppModule.class, DaoModule.class})
public interface AppComponent {
    void inject(MainActivityFragment mainActivityFragment);

    void inject(MainActivityMaterialFragment mainActivityMaterialFragment);

    void inject(AddToDoItemActivity addToDoItemActivity);

    void inject(EditItemActivity editItemActivity);
}
