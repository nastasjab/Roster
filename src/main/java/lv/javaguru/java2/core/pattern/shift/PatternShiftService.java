package lv.javaguru.java2.core.pattern.shift;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Generic;

public interface PatternShiftService extends GenericService {
    Generic getObject(long id, long patternId) throws DBException;
    int getNextSequence (long patternId) throws DBException;
}
