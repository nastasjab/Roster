package lv.javaguru.java2.database;


import lv.javaguru.java2.domain.Shift;
import lv.javaguru.java2.domain.ShiftPattern;

import java.util.List;

public interface ShiftPatternDAO {
    void create(ShiftPattern shift) throws DBException;

    ShiftPattern getById(long id) throws DBException;

    void delete(long id) throws DBException;

    void update(ShiftPattern shift) throws DBException;

    void updateShiftId(long id, long shiftId) throws DBException;

    List<ShiftPattern> getAll(long patternId) throws DBException;

    List<Long> getShiftIdsByPatternId(long id) throws DBException;

    List<Shift> getShiftsByPatternId(long id) throws DBException;

    int getNextSequenceNo(long id) throws DBException;
}
