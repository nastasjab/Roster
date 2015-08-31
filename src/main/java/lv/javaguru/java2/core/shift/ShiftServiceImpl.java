package lv.javaguru.java2.core.shift;

import lv.javaguru.java2.core.GenericServiceImpl;
import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.shift.Shift;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ShiftServiceImpl
        extends GenericServiceImpl<ShiftDAO, Shift> implements ShiftService {

    @Override
    public Shift getNewInstance() {
        return new Shift();
    }

    @Override
    protected void validate(Generic object) throws Exception {
        Shift shift = (Shift)object;
        String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        java.util.regex.Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);

        if (!pattern.matcher(shift.getShiftStarts()).matches())
            throw new Exception("Invalid shift start time. Format: HH:MM");

        if (!pattern.matcher(shift.getShiftStarts()).matches())
            throw new Exception("Invalid shift end time. Format: HH:MM");
    }
}
