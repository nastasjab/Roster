package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.ShiftOnExactDay;

import java.sql.Date;
import java.util.List;

public interface ShiftOnExactDayDAO extends GenericDAO<ShiftOnExactDay>{

    void create(ShiftOnExactDay shiftOnExactDay) throws DBException;

    ShiftOnExactDay getById(long id) throws DBException;

    void delete(long id) throws DBException;

    void update(ShiftOnExactDay shiftOnExactDay) throws DBException;

    List<ShiftOnExactDay> getAll() throws DBException;

    ShiftOnExactDay getShiftOnExactDay(long userId, Date date);

    void setShiftOnExactDay(ShiftOnExactDay shiftOnExactDay);

    List<ShiftOnExactDay> getShiftsOnExactDay(Date from, Date till);
}
