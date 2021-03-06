package lv.javaguru.java2.core.roster;

import lv.javaguru.java2.core.NoShiftFoundException;
import lv.javaguru.java2.domain.roster.Roster;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.user.User;

import java.sql.Date;
import java.util.List;

public interface RosterFactory {
    Roster getRoster(Roster roster);
    Roster getRoster(Roster roster, List<User> forUsers);
    Shift getShift(Date date, long userId) throws NoShiftFoundException;
    List<Shift> getAvailableShifts(Date date, long userId);
    void setShift(Date date, long userId, long shiftId) throws InvalidShiftException;
}
