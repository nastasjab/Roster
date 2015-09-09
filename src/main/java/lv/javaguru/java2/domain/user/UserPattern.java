package lv.javaguru.java2.domain.user;

import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.pattern.Pattern;

import javax.persistence.*;
import java.sql.Date;

import static lv.javaguru.java2.domain.pattern.PatternBuilder.createPattern;

@Entity
@Table(name = "user_patterns")
public class UserPattern extends Generic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    protected long id;

    @Column(name = "userId")
    protected long userId;

    @Column(name = "startDay", columnDefinition = "DATETIME")
    protected Date startDay;

    @Column(name = "endDay", columnDefinition = "DATETIME")
    protected Date endDay;

    @Column(name = "patternStartDay")
    protected int patternStartDay;

    @OneToOne (fetch=FetchType.EAGER)
    @JoinColumn(name = "patternId")
    protected Pattern pattern;

    public UserPattern() {
        super();
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

    public Pattern getPattern() {
        if (pattern == null)
            pattern =  createPattern().build();

        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

}
