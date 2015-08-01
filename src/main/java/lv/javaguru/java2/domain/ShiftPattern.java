package lv.javaguru.java2.domain;

public class ShiftPattern  extends Generic{

    long patternId;
    long shiftId;
    String shiftName;
    int seqNo;

    public ShiftPattern() {
    }

    public long getPatternId() {
        return patternId;
    }

    public void setPatternId(long patternId) {
        this.patternId = patternId;
    }

    public long getShiftId() {
        return shiftId;
    }

    public void setShiftId(long shiftId) {
        this.shiftId = shiftId;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }
}
