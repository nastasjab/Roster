package lv.javaguru.java2.core;


import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.core.user.InvalidEmailException;
import lv.javaguru.java2.core.user.InvalidPhoneException;
import lv.javaguru.java2.core.user.UserValidator;
import lv.javaguru.java2.database.user.UserDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static lv.javaguru.java2.domain.user.UserBuilder.createUser;

public class UserValidatorTest extends GenericSpringTest{
    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserDAO userDAO;

    @Test (expected = EmptyObjectNameException.class)
    public void testLoginValidationEmpty() throws Exception {
        userValidator.validateLogin(createUser().withLogin("").build());
    }

    @Test (expected = ObjectExistException.class)
    public void testLoginValidationAlreadyExists() throws Exception {
        userDAO.create(createUser().withLogin("testLogin1234").build());
        userValidator.validateLogin(createUser().withLogin("testLogin1234").build());
    }

    @Test (expected = EmptyObjectNameException.class)
    public void testValidateFirstNameEmpty() throws Exception {
        userValidator.validateFirstAndLastNames(createUser()
                .withFirstname("")
                .withLastName("testLastname")
                .build());
    }

    @Test (expected = EmptyObjectNameException.class)
    public void testValidateLastNameEmpty() throws Exception {
        userValidator.validateFirstAndLastNames(createUser()
                .withFirstname("testLastname")
                .withLastName("")
                .build());
    }

    @Test (expected = ObjectExistException.class)
    public void testValidateUserWithSuchFirstNameAndLastNameAlreadyExists() throws Exception {
        userDAO.create(createUser().withFirstname("testFirstName").withLastName("testLastName").build());
        userValidator.validateFirstAndLastNames(createUser().withFirstname("testFirstName").withLastName("testLastName").build());
    }

    @Test (expected = InvalidPhoneException.class)
    public void testValidatePhoneNumber() throws Exception {
        userValidator.validatePhone(createUser().withPhone("26541r6541").build());
    }

    @Test
    public void testValidateCorrectPhoneNumber() throws Exception {
        userValidator.validatePhone(createUser().withPhone("+371-265 41 65").build());
    }

    @Test (expected = ObjectExistException.class)
    public void testExistingPhoneNumber() throws Exception {
        userDAO.create(createUser().withPhone("65464565").build());
        userValidator.validatePhone(createUser().withPhone("65464565").build());
    }

    @Test (expected = InvalidEmailException.class)
    public void testValidateEmail() throws Exception {
        userValidator.validateEmail(createUser().withEmail("123_65465.lv").build());
    }

    @Test
    public void testValidateCorrectEmail() throws Exception {
        userValidator.validateEmail(createUser().withEmail("test@testhost.test").build());
    }

    @Test (expected = ObjectExistException.class)
    public void testExistingEmail() throws Exception {
        userDAO.create(createUser().withEmail("test@testhost.test").build());
        userValidator.validateEmail(createUser().withEmail("test@testhost.test").build());
    }

}
