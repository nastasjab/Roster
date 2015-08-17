package lv.javaguru.java2.database;

import org.hibernate.JDBCException;

import java.util.List;

public interface GenericDAO<T> {
    void create(T obj) throws JDBCException, DBException;

    void update(T obj) throws JDBCException, DBException;

    List<T> getAll() throws JDBCException, DBException;

    T getById(long id) throws JDBCException, DBException;

    void delete(long id) throws JDBCException, DBException;
}
