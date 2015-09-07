package lv.javaguru.java2.domain.shift;

import lv.javaguru.java2.domain.Generic;

import javax.persistence.*;

@Entity
@Table(name ="shifts")
public class Shift extends Generic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    protected long id;

    @Column(name = "name")
    protected String name = "";

    @Column(name = "start")
    protected String shiftStarts = "";

    @Column(name = "end")
    protected String shiftEnds = "";


    public Shift() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShiftEnds() {
        return shiftEnds;
    }

    public void setShiftEnds(String shiftEnds) {
        this.shiftEnds = shiftEnds;
    }

    public String getShiftStarts() {
        return shiftStarts;
    }

    public void setShiftStarts(String shiftStarts) {
        this.shiftStarts = shiftStarts;
    }

}
