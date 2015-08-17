package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.PatternShift;
import org.hibernate.JDBCException;

import java.util.List;

public interface PatternShiftDAO extends GenericDAO<PatternShift> {

    List<PatternShift> getAll(long patternId) throws JDBCException, DBException;

    void deleteByPatternId(long patternId) throws JDBCException, DBException;

    int getNextSequenceNo(long patternId) throws JDBCException, DBException;
}
