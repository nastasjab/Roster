package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.ShiftOnExactDay;

import java.sql.Date;
import java.util.List;

public interface ShiftOnExactDayDAO extends GenericDAO<ShiftOnExactDay>{
    ShiftOnExactDay getShiftOnExactDay(long userId, Date date);

    void setShiftOnExactDay(ShiftOnExactDay shiftOnExactDay);

    List<ShiftOnExactDay> getShiftsOnExactDay(Date from, Date till);
}
