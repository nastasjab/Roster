package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Shift;

import java.util.List;

public interface ShiftDAO {

    void create(Shift shift) throws DBException;

    Shift getById(long id) throws DBException;

    void delete(long id) throws DBException;

    void update(Shift shift) throws DBException;

    List<Shift> getAll() throws DBException;
}
