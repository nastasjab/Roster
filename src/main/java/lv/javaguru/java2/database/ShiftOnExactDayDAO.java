package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.ShiftOnExactDay;

import java.util.List;

public interface ShiftOnExactDayDAO {

    void create(ShiftOnExactDay shiftOnExactDay) throws DBException;

    ShiftOnExactDay getById(long id) throws DBException;

    void delete(long id) throws DBException;

    void update(ShiftOnExactDay shiftOnExactDay) throws DBException;

    List<ShiftOnExactDay> getAll() throws DBException;
}
