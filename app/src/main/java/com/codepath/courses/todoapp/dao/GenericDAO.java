package com.codepath.courses.todoapp.dao;


public interface GenericDAO<T> {

    Long save(T newInstance);

    T get(Long id);

    void update(T transientObject);

    void delete(Long id);

}