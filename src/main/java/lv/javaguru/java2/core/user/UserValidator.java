package lv.javaguru.java2.core.user;

import lv.javaguru.java2.domain.user.User;

public interface UserValidator {
    void validateLogin(User user) throws Exception;
    void validateFirstAndLastNames(User user) throws Exception;
    void validatePhone(User user) throws Exception;
    void validateEmail(User user) throws Exception;
}
