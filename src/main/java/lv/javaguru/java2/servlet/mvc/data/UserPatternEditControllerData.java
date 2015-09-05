package lv.javaguru.java2.servlet.mvc.data;

import lv.javaguru.java2.domain.pattern.Pattern;
import lv.javaguru.java2.domain.user.User;
import lv.javaguru.java2.domain.user.UserPattern;

import java.util.ArrayList;
import java.util.List;

public class UserPatternEditControllerData {

    private User user;
    private UserPattern userPattern;
    private List<Pattern> patterns;
    public UserPatternEditControllerData() {
        this.user = new User();
        this.userPattern = new UserPattern();
        this.patterns = new ArrayList<Pattern>();
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

    public List<Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<Pattern> patterns) {
        this.patterns = patterns;
    }
}
