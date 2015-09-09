package lv.javaguru.java2.servlet.mvc.data;


import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.roster.SingleShift;
import lv.javaguru.java2.domain.user.User;

import java.sql.Date;
import java.util.List;

public class SetShiftControllerData {

    User user;
    List<Shift> shifts;
    long currentShiftId;
    Date date;

    public SetShiftControllerData() {
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

    public long getCurrentShiftId() {
        return currentShiftId;
    }

    public void setCurrentShiftId(long currentShiftId) {
        this.currentShiftId = currentShiftId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
