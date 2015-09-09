package lv.javaguru.java2.database.roster;

import lv.javaguru.java2.database.GenericDAO;
import lv.javaguru.java2.domain.roster.SingleShift;

import java.sql.Date;
import java.util.List;

public interface SingleShiftDAO extends GenericDAO<SingleShift> {
    SingleShift getSingleShift(long userId, Date date) throws IndexOutOfBoundsException;

    void setSingleShift(SingleShift singleShift);

    List<SingleShift> getSingleShift(Date from, Date till);
}
