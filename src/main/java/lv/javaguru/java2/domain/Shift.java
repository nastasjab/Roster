package lv.javaguru.java2.domain;

import javax.persistence.*;

@Entity
@Table(name ="shifts")
public class Shift extends Generic{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name")
    private String name = "";

    @Column(name = "start")
    private String shiftStarts = "";

    @Column(name = "end")
    private String shiftEnds = "";


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
