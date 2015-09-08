package lv.javaguru.java2.domain.pattern;


import java.util.List;

public class PatternBuilder extends Pattern {
    private PatternBuilder() {}

    public static PatternBuilder createPattern() {
        return new PatternBuilder();
    }

    public Pattern build() {
        Pattern pattern = new Pattern();
        pattern.setId(id);
        if (name==null)
            pattern.setName("");
        else
            pattern.setName(name);

            pattern.setPatternShifts(patternShifts);
        return pattern;
    }

    public PatternBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PatternBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PatternBuilder withPatternShifts(List<PatternShift> patternShifts) {
        this.patternShifts = patternShifts;
        return this;
    }
}
