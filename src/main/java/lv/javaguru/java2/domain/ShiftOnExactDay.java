package lv.javaguru.java2.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "shifts_on_exact_day")
public class ShiftOnExactDay extends Generic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "userId")
    private long userId;

    @Column(name = "date")
    private Date date;

    @ManyToOne (fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shiftId")
    private Shift shift;

    public ShiftOnExactDay() {
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
        if (shift==null) shift = new Shift();

        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
