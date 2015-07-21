package lv.javaguru.java2.domain;

import java.sql.Date;

public class ShiftOnExactDay extends Generic{

    private long userId;
    private Date date;
    private long ahiftId;

    public ShiftOnExactDay() {
        super();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getShiftId() {
        return ahiftId;
    }

    public void setShiftId(long shiftId) {
        this.ahiftId = shiftId;
    }
}
