package lv.javaguru.java2.servlet.mvc.data;

import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.domain.UserPattern;

import java.util.List;

public class UserPatternControllerData {

    private User user;
    private List<UserPattern> userPatterns;
    private List<Pattern> patterns;

    public UserPatternControllerData() {
    }

    public UserPatternControllerData(User user, List<UserPattern> userPatterns, List<Pattern> patterns) {
        this.user = user;
        this.userPatterns = userPatterns;
        this.patterns = patterns;
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

    public List<Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    public Pattern getPatternById(long id) throws Exception {
        for (Pattern pattern : patterns)
            if (id == pattern.getId())
                return pattern;
        throw new Exception("No such pattern");
    }
}
