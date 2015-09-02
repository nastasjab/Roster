package lv.javaguru.java2.core.shift;

import lv.javaguru.java2.core.*;
import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.shift.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ShiftServiceImpl
        extends GenericServiceImpl<ShiftDAO, Shift> implements ShiftService {

    @Autowired
    ShiftValidator shiftValidator;

    @Override
    public Shift getNewInstance() {
        return new Shift();
    }

    @Override
    public void validate(Generic object, boolean add) throws Exception {
        Shift shift = (Shift)object;

        shiftValidator.validateStartTime(shift.getShiftStarts());
        shiftValidator.validateEndTime(shift.getShiftEnds());
        shiftValidator.validateName(shift.getName(), shift.getId(), add);
    }




}
