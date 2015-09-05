package lv.javaguru.java2.database.roster;

import lv.javaguru.java2.database.GenericDAO;
import lv.javaguru.java2.domain.roster.SingleShift;

import java.sql.Date;
import java.util.List;

public interface SingleShiftDAO extends GenericDAO<SingleShift> {
    SingleShift getShiftOnExactDay(long userId, Date date);

    void setShiftOnExactDay(SingleShift singleShift);

    List<SingleShift> getShiftsOnExactDay(Date from, Date till);
}
