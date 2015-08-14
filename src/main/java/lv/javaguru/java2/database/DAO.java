package lv.javaguru.java2.database;

import java.util.List;

public interface DAO<T> {
    void create(T shift) throws DBException;

    void update(T shift) throws DBException;

    List<T> getAll() throws DBException;

    T getById(long id) throws DBException;

    void delete(long id) throws DBException;
}
