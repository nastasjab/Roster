package lv.javaguru.java2.servlet.mvc.data;


import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.roster.SingleShift;
import lv.javaguru.java2.domain.user.User;

import java.sql.Date;
import java.util.List;

public class SingleShiftsControllerData {

    User user;
    List<Shift> shifts;
    long currentShiftId;
    Date date;

    public SingleShiftsControllerData() {
    }

    public long getCurrentShiftId() {
        return currentShiftId;
    }

    public void setCurrentShiftId(long currentShiftId) {
        this.currentShiftId = currentShiftId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Shift getShiftById(long id) throws Exception {
        for (Shift shift : shifts)
            if (shift.getId() == id)
                return shift;
        throw new Exception("Shift not found");
    }
}
