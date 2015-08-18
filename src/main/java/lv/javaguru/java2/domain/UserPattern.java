package lv.javaguru.java2.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "user_patterns")
public class UserPattern extends Generic{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "userId")
    private long userId;

    @Column(name = "startDay", columnDefinition = "DATETIME")
    private Date startDay;

    @Column(name = "endDay", columnDefinition = "DATETIME")
    private Date endDay;

    @Column(name = "patternStartDay")
    private int patternStartDay;

    @ManyToOne (fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "patternShiftId")
    private PatternShift patternShift;

    public UserPattern() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public PatternShift getPatternShift() {
        if (patternShift == null)
            patternShift =  new PatternShift();

        return patternShift;
    }

    public void setPatternShift(PatternShift patternShift) {
        this.patternShift = patternShift;
    }

}
