package lv.javaguru.java2.domain;

import javax.persistence.*;

@Entity
@Table(name ="patterns_shifts")
public class PatternShift extends Generic{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

  //  @Column(name = "pattern")
  //  private long pattern;

    @ManyToOne (fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shiftId")
    private Shift shift;

    @Column(name = "seqNo")
    private int seqNo;

    @ManyToOne (fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="patternId")
    private Pattern pattern;

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
            this.pattern = new Pattern();
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        if (this.pattern == null)
            this.pattern = new Pattern();

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
            shift =  new Shift();

        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

}
