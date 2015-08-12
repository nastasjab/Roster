package lv.javaguru.java2.domain;

import java.sql.Date;

public class UserPattern extends Generic{
    private long userId;
    private long shiftPatternId;
    private String shiftPatternName = "";
    private Date startDay;
    private Date endDay;
    private int patternStartDay = 1;

    public UserPattern() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getShiftPatternId() {
        return shiftPatternId;
    }

    public void setShiftPatternId(long shiftPatternId) {
        this.shiftPatternId = shiftPatternId;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public int getPatternStartDay() {
        return patternStartDay;
    }

    public void setPatternStartDay(int patternStartDay) {
        this.patternStartDay = patternStartDay;
    }

    public String getShiftPatternName() {
        return shiftPatternName;
    }

    public void setShiftPatternName(String shiftPatternName) {
        this.shiftPatternName = shiftPatternName;
    }
}
