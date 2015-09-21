package lv.javaguru.java2.servlet.mvc.data;

import lv.javaguru.java2.domain.roster.Roster;
import lv.javaguru.java2.domain.shift.Shift;

import java.util.List;

public class RosterControllerData {

    Roster roster;
    List<Shift> shifts;

    public RosterControllerData() {
    }

    public Roster getRoster() {
        return roster;
    }

    public void setRoster(Roster roster) {
        this.roster = roster;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }
}
