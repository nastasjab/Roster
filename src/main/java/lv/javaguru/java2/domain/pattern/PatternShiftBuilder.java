package lv.javaguru.java2.domain.pattern;


import lv.javaguru.java2.domain.shift.Shift;

public class PatternShiftBuilder extends PatternShift {
    private PatternShiftBuilder() {}

    public static PatternShiftBuilder createPatternShift() {
        return new PatternShiftBuilder();
    }

    public PatternShift build() {
        PatternShift patternShift = new PatternShift();
        patternShift.setId(id);
        patternShift.setShift(shift);
        patternShift.setSeqNo(seqNo);
        patternShift.setPattern(pattern);
        return patternShift;
    }

    public PatternShiftBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PatternShiftBuilder withShift(Shift shift) {
        this.shift = shift;
        return this;
    }

    public PatternShiftBuilder withShiftId(long shiftId) {
        this.getShift().setId(shiftId);
        return this;
    }

    public PatternShiftBuilder withSeqNo(int seqNo) {
        this.seqNo = seqNo;
        return this;
    }

    public PatternShiftBuilder withPattern(Pattern pattern) {
        this.pattern = pattern;
        return this;
    }

    public PatternShiftBuilder withPatternId(long patternId) {
        this.getPattern().setId(patternId);
        return this;
    }
}
