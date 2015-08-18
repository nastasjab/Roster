package lv.javaguru.java2.servlet.mvc.data;

import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.domain.UserPattern;

import java.util.List;

public class UserPatternControllerData {

    private User user;
    private List<UserPattern> userPatterns;

    public UserPatternControllerData() {
    }

    public UserPatternControllerData(User user, List<UserPattern> userPatterns) {
        this.user = user;
        this.userPatterns = userPatterns;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserPattern> getUserPatterns() {
        return userPatterns;
    }

    public void setUserPatterns(List<UserPattern> userPatterns) {
        this.userPatterns = userPatterns;
    }

}
