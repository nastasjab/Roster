package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.PatternShift;

import java.util.List;

public interface PatternShiftDAO extends GenericDAO<PatternShift> {

    List<PatternShift> getAll(long patternId) throws DBException;

    void deleteByPatternId(long patternId) throws DBException;
}
