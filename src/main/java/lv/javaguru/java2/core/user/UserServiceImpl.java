package lv.javaguru.java2.core.user;

import lv.javaguru.java2.core.GenericServiceImpl;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static lv.javaguru.java2.domain.user.UserBuilder.createUser;

@Component
public class UserServiceImpl extends GenericServiceImpl<UserDAO, User> implements UserService {

    @Autowired
    private UserValidator userValidator;

    @Override
    public void validate(Generic object, boolean add) throws Exception {
        User user = (User) object;

        userValidator.validateLogin(user.getLogin(), add);
        userValidator.validateFirstAndLastNames(user.getFirstName(), user.getLastName(), add);
        userValidator.validatePhone(user.getPhone());
        userValidator.validateEmail(user.getEmail());

    }

    @Override
    public User getNewInstance() {
        return createUser().build();
    }

    public List<User> getAllSorted() throws Exception {
        return dao.getAllSorted();
    }
}
