package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.User;

import java.util.List;

public interface UserDAO {

    User create(User user) throws DBException;

    User getById(long id) throws DBException;

    void delete(long id) throws DBException;

    void update(User user) throws DBException;

    List<User> getAll() throws DBException;

}
