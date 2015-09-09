package lv.javaguru.java2.core.shift;

import lv.javaguru.java2.core.EmptyIdentifierException;
import lv.javaguru.java2.core.ObjectExistException;
import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.domain.shift.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ShiftValidatorImpl implements ShiftValidator {
    private final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    private final java.util.regex.Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);

    @Autowired
    ShiftDAO shiftDAO;

    public void validateStartTime(String startTime) throws Exception {
        if (startTime!=null && !startTime.isEmpty())
            if (!pattern.matcher(startTime).matches())
                throw new InvalidTimeFormatException("shift start");
    }

    public void validateEndTime(String endTime) throws Exception {
        // allow empty end time
        if (endTime!=null && !endTime.isEmpty())
            if (!pattern.matcher(endTime).matches())
                throw new InvalidTimeFormatException("shift end");
    }

    public void validateName(String name, long id, boolean add) throws Exception {
        if (name==null || name.isEmpty())
            throw new EmptyIdentifierException("shift");

        Shift shiftFromDb = shiftDAO.getByObjectName(name);

        if (add && shiftFromDb!=null)
            throw new ObjectExistException(name);

        if (!add && shiftFromDb!=null && shiftFromDb.getId()!=id)
            throw new ObjectExistException(name);
    }
}
