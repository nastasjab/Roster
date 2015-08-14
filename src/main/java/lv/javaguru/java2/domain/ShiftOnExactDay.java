package lv.javaguru.java2.domain;

import java.sql.Date;

@Deprecated
public class ShiftOnExactDay extends Generic{

    private long userId;
    private Date date;
    private long shiftId;

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
        return shiftId;
    }

    public void setShiftId(long shiftId) {
        this.shiftId = shiftId;
    }
}
