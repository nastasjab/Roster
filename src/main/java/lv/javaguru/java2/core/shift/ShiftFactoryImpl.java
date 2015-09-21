package lv.javaguru.java2.core.shift;

import lv.javaguru.java2.core.*;
import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.servlet.mvc.data.RosterControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;

@Component
public class ShiftFactoryImpl
        extends GenericFactoryImpl<ShiftDAO, Shift> implements ShiftFactory {

    @Autowired
    ShiftValidator shiftValidator;

    @Override
    public Shift getNewInstance() {
        return createShift().build();
    }

    @Override
    public void validate(Generic object, boolean add) throws Exception {
        Shift shift = (Shift)object;

        shiftValidator.validateStartTime(shift.getShiftStarts());
        shiftValidator.validateEndTime(shift.getShiftEnds());
        shiftValidator.validateName(shift.getName(), shift.getId(), add);
    }

    public List<Shift> getWorkingShifts() {
        List<Shift> shifts = new ArrayList<Shift>();
        for (Shift shift : dao.getAll())
            if (!shift.getShiftStarts().isEmpty() && !shift.getShiftEnds().isEmpty())
                shifts.add(shift);
        return shifts;
    }



}
