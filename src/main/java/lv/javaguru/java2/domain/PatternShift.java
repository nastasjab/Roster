package lv.javaguru.java2.domain;

import javax.persistence.*;

@Entity
@Table(name ="patterns_shifts")
public class PatternShift extends Generic{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "patternId")
    private long patternId;

    @ManyToOne (fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shiftId")
    private Shift shift;

    @Column(name = "seqNo")
    private int seqNo;

    public PatternShift() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPatternId() {
        return patternId;
    }

    public void setPatternId(long patternId) {
        this.patternId = patternId;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public Shift getShift() {
        if (shift == null)
            shift =  new Shift();

        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

}
