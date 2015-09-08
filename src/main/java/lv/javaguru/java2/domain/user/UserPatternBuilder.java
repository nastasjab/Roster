package lv.javaguru.java2.domain.user;


import lv.javaguru.java2.domain.pattern.Pattern;

import java.sql.Date;

import static lv.javaguru.java2.domain.pattern.PatternBuilder.createPattern;

public class UserPatternBuilder extends UserPattern{

    private UserPatternBuilder() {}

    public static UserPatternBuilder createUserPattern() { return new UserPatternBuilder(); }

    public UserPattern build() {

        UserPattern userPattern = new UserPattern();
        userPattern.setId(id);
        userPattern.setUserId(userId);
        userPattern.setStartDay(startDay);
        userPattern.setEndDay(endDay);
        userPattern.setPatternStartDay(patternStartDay);
        if (pattern == null)
            userPattern.setPattern(createPattern().build());
        else
            userPattern.setPattern(pattern);
        return userPattern;

    }

    public UserPatternBuilder withUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public UserPatternBuilder withStartDay(Date startDay) {
        this.startDay = startDay;
        return this;
    }

    public UserPatternBuilder withEndDay(Date endDay) {
        this.endDay = endDay;
        return this;
    }

    public UserPatternBuilder withPatternStartDay(int patternStartDay) {
        this.patternStartDay = patternStartDay;
        return this;
    }

    public UserPatternBuilder withPattern(Pattern pattern) {
        this.pattern = pattern;
        return this;
    }


}
