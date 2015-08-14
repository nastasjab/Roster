package lv.javaguru.java2.servlet.mvc.data;

import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.domain.UserPattern;

import java.util.List;

public class UserPatternEditControllerData {

    private User user;
    private UserPattern userPattern;
    private List<Pattern> shiftPatterns;

    public UserPatternEditControllerData(User user, UserPattern userPattern, List<Pattern> shiftPatterns) {
        this.user = user;
        this.userPattern = userPattern;
        this.shiftPatterns = shiftPatterns;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserPattern getUserPattern() {
        return userPattern;
    }

    public void setUserPattern(UserPattern userPattern) {
        this.userPattern = userPattern;
    }

    public List<Pattern> getShiftPatterns() {
        return shiftPatterns;
    }

    public void setShiftPatterns(List<Pattern> shiftPatterns) {
        this.shiftPatterns = shiftPatterns;
    }
}
