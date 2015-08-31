package lv.javaguru.java2.domain.pattern;

import lv.javaguru.java2.domain.Generic;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="patterns")
public class Pattern extends Generic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name")
    private String name = "";

    @OneToMany(mappedBy="pattern", fetch=FetchType.EAGER)
    private List<PatternShift> patternShifts;

    public Pattern() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<PatternShift> getPatternShifts() {
        return patternShifts;
    }

    public void setPatternShifts(List<PatternShift> patternShifts) {
        this.patternShifts = patternShifts;
    }
}
