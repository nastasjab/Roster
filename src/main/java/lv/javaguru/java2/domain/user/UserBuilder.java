package lv.javaguru.java2.domain.user;


import java.sql.Date;

public class UserBuilder extends User{

    private UserBuilder() {}

    public static UserBuilder createUser() { return new UserBuilder(); }

    public User build() {

        User user  = new User();
        user.setId(id);

        if (login == null)
            user.setLogin("");
        else
            user.setLogin(login);

        if (password == null)
            user.setPassword("");
        else
            user.setPassword(password);

        if (userType == null)
            user.setUserType("");
        else
            user.setUserType(userType);

        if (firstName == null)
            user.setFirstName("");
        else
            user.setFirstName(firstName);

        if (lastName == null)
            user.setLastName("");
        else
            user.setLastName(lastName);

        if (email == null)
            user.setEmail("");
        else
            user.setEmail(email);

        if (phone == null)
            user.setPhone("");
        else
            user.setPhone(phone);

        user.setRosterShowStartDate(rosterShowStartDate);
        user.setRosterShowEndDate(rosterShowEndDate);

        return user;

    }

    public UserBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withUsertype(String userType) {
        this.userType = userType;
        return this;
    }

    public UserBuilder withFirstname(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserBuilder withRosterShowStartDate(Date rosterShowStartDate) {
        this.rosterShowStartDate = rosterShowStartDate;
        return this;
    }

    public UserBuilder withRosterShowEndDate(Date rosterShowEndDate) {
        this.rosterShowEndDate = rosterShowEndDate;
        return this;
    }

}
