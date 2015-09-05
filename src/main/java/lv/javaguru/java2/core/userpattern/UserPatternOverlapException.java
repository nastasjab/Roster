package lv.javaguru.java2.core.userpattern;


import lv.javaguru.java2.domain.user.UserPattern;

import java.util.List;

public class UserPatternOverlapException extends Exception{
    public UserPatternOverlapException() {
        super("Patterns overlap!");
    }

    public UserPatternOverlapException(List<UserPattern> overlapsWith) throws UserPatternOverlapException{
        StringBuilder s = new StringBuilder();
        for (UserPattern userPattern : overlapsWith) {
            s.append("from ");
            s.append(userPattern.getStartDay());
            s.append(" till ");
            s.append(userPattern.getEndDay());
            s.append("<br>");
        }

        throw new UserPatternOverlapException(s.toString());
    }

    private UserPatternOverlapException(String s) {
        super(String.format("Pattern overlaps with following patterns:\n'%s'", s));
    }
}
