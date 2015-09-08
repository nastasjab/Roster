package lv.javaguru.java2.domain.roster;

import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.shift.Shift;

import javax.persistence.*;
import java.sql.Date;

import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;

@Entity
@Table(name = "single_shifts")
public class SingleShift extends Generic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    protected long id;

    @Column(name = "userId")
    protected long userId;

    @Column(name = "date")
    protected Date date;

    @ManyToOne (fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shiftId")
    protected Shift shift;

    public SingleShift() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Shift getShift() {
        if (shift==null) shift = createShift();

        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
