package lv.javaguru.java2.core.pattern;

import lv.javaguru.java2.core.GenericServiceImpl;
import lv.javaguru.java2.database.pattern.PatternShiftDAO;
import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.pattern.PatternShift;
import lv.javaguru.java2.servlet.mvc.data.PatternShiftEditControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static lv.javaguru.java2.domain.pattern.PatternShiftBuilder.createPatternShift;

@Component
public class PatternShiftFactoryImpl
        extends GenericServiceImpl<PatternShiftDAO, PatternShift> implements PatternShiftFactory {

    @Autowired
    private PatternShiftDAO patternShiftDAO;

    @Autowired
    private ShiftDAO shiftDAO;

    public Generic getObject(long id, long patternId) {
        PatternShiftEditControllerData result = new PatternShiftEditControllerData();
        try {
            PatternShift patternShift = patternShiftDAO.getById(id);
            result.setId(patternShift.getId());
            result.setPattern(patternShift.getPattern());
            result.setSeqNo(patternShift.getSeqNo());
            result.getShift().setName(patternShift.getShift().getName());
            result.getShift().setId(patternShift.getShift().getId());

        } catch (NullPointerException e) {
            result = new PatternShiftEditControllerData();
            result.getPattern().setId(patternId);
            result.setSeqNo(patternShiftDAO.getNextSequenceNo(patternId));
        }

        result.setAllShifts(shiftDAO.getAll());
        return result;
    }

    public int getNextSequence(long patternId)  {
        return patternShiftDAO.getNextSequenceNo(patternId);
    }

    @Override
    public PatternShift getNewInstance() {
        return createPatternShift().build();
    }

    @Override
    public void validate(Generic object, boolean add) throws Exception {    }
}
