package lv.javaguru.java2.domain.user;

import lv.javaguru.java2.domain.Generic;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users")
public class User extends Generic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    protected long id;

    @Column(name = "login")
    protected String login;

    @Column(name = "password")
    protected String password;

    @Column(name = "userType", columnDefinition = "CHAR")
    protected String userType;

    @Column(name = "firstName")
    protected String firstName;

    @Column(name = "lastName")
    protected String lastName;

    @Column(name = "email")
    protected String email;

    @Column(name = "phone")
    protected String phone;

    @Column(name = "rosterShowStartDate", columnDefinition = "DATETIME")
    protected Date rosterShowStartDate;

    @Column(name = "rosterShowEndDate", columnDefinition = "DATETIME")
    protected Date rosterShowEndDate;

    public User() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRosterShowStartDate() {
        return rosterShowStartDate;
    }

    public void setRosterShowStartDate(Date rosterShowStartDate) {
        this.rosterShowStartDate = rosterShowStartDate;
    }

    public Date getRosterShowEndDate() {
        return rosterShowEndDate;
    }

    public void setRosterShowEndDate(Date rosterShowEndDate) {
        this.rosterShowEndDate = rosterShowEndDate;
    }
}
