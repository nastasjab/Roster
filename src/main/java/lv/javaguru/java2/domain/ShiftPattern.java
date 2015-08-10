package lv.javaguru.java2.domain;

public class ShiftPattern  extends Generic{

    private long patternId;
    private long shiftId;
    private String shiftName;
    private int seqNo;

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
