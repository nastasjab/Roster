package lv.javaguru.java2.database;

import java.util.List;

public interface GenericDAO<T> {
    void create(T obj) throws DBException;

    void update(T obj) throws DBException;

    List<T> getAll() throws DBException;

    T getById(long id) throws DBException;

    void delete(long id) throws DBException;
}
