package lv.javaguru.java2.core.user;

import lv.javaguru.java2.core.GenericFactoryImpl;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static lv.javaguru.java2.domain.user.UserBuilder.createUser;

@Component
public class UserFactoryImpl extends GenericFactoryImpl<UserDAO, User> implements UserFactory {

    @Autowired
    private UserValidator userValidator;

    @Override
    public void validate(Generic object, boolean add) throws Exception {
        User user = (User) object;

        userValidator.validateLogin(user);
        userValidator.validateFirstAndLastNames(user);
        userValidator.validatePhone(user);
        userValidator.validateEmail(user);

    }

    @Override
    public User getNewInstance() {
        return createUser().build();
    }

    public List<User> getAllSorted() throws Exception {
        return dao.getAllSorted();
    }
}
