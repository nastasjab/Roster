package lv.javaguru.java2.database.pattern;

import lv.javaguru.java2.database.GenericDAO;
import lv.javaguru.java2.domain.pattern.PatternShift;
import org.hibernate.JDBCException;

import java.util.List;

public interface PatternShiftDAO extends GenericDAO<PatternShift> {

    List<PatternShift> getAll(long patternId) throws JDBCException;

    void deleteByPatternId(long patternId) throws JDBCException;

    int getNextSequenceNo(long patternId) throws JDBCException;
}
