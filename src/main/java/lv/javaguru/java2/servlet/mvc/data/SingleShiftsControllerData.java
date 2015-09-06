package lv.javaguru.java2.servlet.mvc.data;


import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.roster.SingleShift;
import lv.javaguru.java2.domain.user.User;

import java.util.List;

public class SingleShiftsControllerData {

    User user;
    List<Shift> shifts;
    SingleShift singleShift;

    public SingleShiftsControllerData() {
    }

    public SingleShift getSingleShift() {
        return singleShift;
    }

    public void setSingleShift(SingleShift singleShift) {
        this.singleShift = singleShift;
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
