package lv.javaguru.java2.core.user;

import lv.javaguru.java2.core.ObjectExistException;

public interface UserValidator {
    void validateLogin(String userName, boolean add) throws Exception;
    void validateFirstAndLastNames(String firtsName, String lastName, boolean add) throws Exception;
    void validatePhone(String phone) throws Exception;
    void validateEmail(String email) throws Exception;
}
