package lv.javaguru.java2.domain.pattern;

import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.shift.Shift;

import javax.persistence.*;

import static lv.javaguru.java2.domain.pattern.PatternBuilder.createPattern;
import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;

@Entity
@Table(name ="patterns_shifts")
public class PatternShift extends Generic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    protected long id;

    @ManyToOne (fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shiftId")
    protected Shift shift;

    @Column(name = "seqNo")
    protected int seqNo;

    @ManyToOne (fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="patternId")
    protected Pattern pattern;

    public PatternShift() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Pattern getPattern() {
        if (this.pattern == null)
            this.pattern = createPattern().build();
        return pattern;
    }

    public void setPattern(Pattern pattern) {
            this.pattern = pattern;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public Shift getShift() {
        if (shift == null)
            shift = createShift().build();

        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

}
