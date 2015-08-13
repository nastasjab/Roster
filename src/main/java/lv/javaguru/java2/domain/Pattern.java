package lv.javaguru.java2.domain;


import java.util.ArrayList;
import java.util.List;

public class Pattern extends Generic{

    private String name = "";

    private List<ShiftPattern> shiftPatterns;

    public Pattern() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShiftPattern> getShiftPatterns() {
        if (shiftPatterns == null)
            shiftPatterns = new ArrayList<ShiftPattern>();

        return shiftPatterns;
    }

    public void setShiftPatterns(List<ShiftPattern> shiftPatterns) {
        this.shiftPatterns = shiftPatterns;
    }

    public long getShiftIdBySeqNo(int seqNo) throws NoSuchFieldException {
        for (ShiftPattern s : shiftPatterns) {
            if (seqNo == s.getSeqNo()) return s.getShiftId();
        }
        throw new NoSuchFieldException();
    }

}
