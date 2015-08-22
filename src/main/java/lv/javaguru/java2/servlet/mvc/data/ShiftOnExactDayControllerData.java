package lv.javaguru.java2.servlet.mvc.data;


import lv.javaguru.java2.domain.Shift;
import lv.javaguru.java2.domain.ShiftOnExactDay;
import lv.javaguru.java2.domain.User;

import java.util.List;

public class ShiftOnExactDayControllerData {

    User user;
    List<Shift> shifts;
    ShiftOnExactDay shiftOnExactDay;

    public ShiftOnExactDayControllerData() {
    }

    public ShiftOnExactDay getShiftOnExactDay() {
        return shiftOnExactDay;
    }

    public void setShiftOnExactDay(ShiftOnExactDay shiftOnExactDay) {
        this.shiftOnExactDay = shiftOnExactDay;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public Shift getShiftById(long id) throws Exception {
        for (Shift shift : shifts)
            if (shift.getId() == id)
                return shift;
        throw new Exception("Shift not found");
    }
}
